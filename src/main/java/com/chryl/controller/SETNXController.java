package com.chryl.controller;

import com.chryl.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁:自实现和发现的问题,和最终lua版,最终效果
 * redis: set nx
 * nx : not exist , 不存在的时候才去set,有值则不去set,该操作为原子操作
 * xx :
 * <p>
 * Created by Chr.yl on 2021/5/3.
 *
 * @author Chr.yl
 */
@RestController
@RequestMapping("set_nx")
public class SETNXController {

    static String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";// lua脚本，用来释放分布式锁

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisCache redisCache;

    @GetMapping("get_nx")
    public Object show() {
        List<Object> list = redisCache.getCacheList("testKey");
        if (list.isEmpty()) {
            //如果为空
            List returnList = queryRedisLock();
            return returnList;
        }
        return list;
    }

    public List queryRedisLock() {
        //redis分布式锁:set nx ,并且直接设置过期时间,这里的时间尽可能大,因为如果锁时间过期,业务还没有完成,会有一个锁时间续期的问题
//        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", "111", 10, TimeUnit.SECONDS);
        String uuid = UUID.randomUUID().toString();
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid, 100, TimeUnit.SECONDS);
        if (lock) {
            System.out.println("获取分布式锁成功....");
            //加锁成功
            //设置过期时间,不可以在这里设置,方式宕机死锁
//            stringRedisTemplate.expire("lock", 10, TimeUnit.SECONDS);
            List<String> dateFromDb;
            try {
                dateFromDb = getDateFromDb();
            } finally {
                //删除成功返回1,删除失败返回0,而且是Long.
                //第二个参数为集合,为删除的key
                //加锁和删锁都要保证原子性
                Long lock1 = stringRedisTemplate.execute(new DefaultRedisScript<Long>(luaScript, Long.class)
                        , Arrays.asList("lock"), uuid);
            }
//            stringRedisTemplate.delete("lock");//删除锁
//            String queryVal = stringRedisTemplate.opsForValue().get("lock");
//            if (uuid.equals(queryVal)) {
//                //删除自己的锁
//                stringRedisTemplate.delete("lock");
//            }
            /**
             * 注意这里的几个问题:
             * 1.万一这里出异常了,没有解锁,就出现了死锁的问题. 解决:设置锁的过期时间,即使没删除,也会自动删除
             * 2.如果还没来得及设置过期时间就宕机了,怎么办? 解决:使用原子操作,set的时候就进行设置时间
             * 3.设置了锁的过期时间的话,如果业务时间过久,业务没完,锁时间过期了,这时候下一个又开始访问和进行加锁,
             * 等第一个执行完,并且也会删掉别人的锁(别人的还没执行完). 解决:设置锁的value为uuid ,先查询,再删除锁
             * 4.删除锁的期间锁过期了,那就又不会删除自己的锁了. 解决:没有保证原子性的原因,获取锁,对比成功,才能删除自己的锁,必须使用lua脚本了
             */

            return dateFromDb;
        } else {
            //加锁失败,重试:如果取锁失败,选择自旋的方式,一直重试,直到拿到锁
            try {
                //休眠100ms
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("获取分布式锁失败..等待重试..");
            return queryRedisLock();
        }

    }

    public List<String> getDateFromDb() {
        //拿到锁之后,再判断缓存中是有,如果没有,再去查db
        List<Object> list = redisCache.getCacheList("testKey");
        if (!list.isEmpty()) {
            //如果不为空,直接返回
//                List returnList = orderService.query();
//                return returnList;
        }
        //如果还是没有,查询db
        List<String> queryList = new ArrayList<>();
        //放入缓存
        redisCache.setCacheList("testKey", queryList);
        return queryList;
    }

}
