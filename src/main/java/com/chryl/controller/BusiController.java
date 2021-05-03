package com.chryl.controller;

import com.chryl.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * redis单体解决:缓存穿透,缓存雪崩,缓存击穿
 * 本地锁,只能锁住当前的进程(单体),当然分布式锁的性能比较慢.
 * <p>
 * <p>
 * 缓存穿透:
 * 大量请求查询一定不存在的数据,所以缓存一定不会命中,导致都去查db
 * 如,表中只有7000条数据,查询id=10000的数据,就一定不存在,这是大量的请求一起访问.
 * 解决办法:把db查不到的也存到缓存,就不用担心缓存穿透,5分钟即可.
 * <p>
 * 缓存雪崩:
 * 缓存中大量(大面积)的key在相同时间过期,导致都去查db
 * 解决办法:失效时间随机,比如1-5分钟.
 * <p>
 * 缓存击穿:
 * 指的是某一个key失效,但是这个key又是高并发的热点数据.大量访问之前刚好失效了
 * 热点key,如iPhone刚上架,大量的用户都会去查iphone,比如缓存时间为1天.
 * 第二天,又有用户去查询iPhone,又导致大量用户都去访问db
 * <p>
 * <p>
 * Created by Chr.yl on 2021/5/3.
 *
 * @author Chr.yl
 */
@RestController
@RequestMapping("busi")
public class BusiController {

    @Autowired
    private RedisCache redisCache;


    @GetMapping("get_show")
    public Object show() {
        List<Object> list = redisCache.getCacheList("testKey");
        if (list.isEmpty()) {
            //如果为空
            List returnList = queryDb();
            return returnList;
        }
        return list;
    }


    /**
     * 模拟查询db ,注意 sync的范围 和拿到锁再去查缓存
     *
     * redis进行set数据时,如果释放锁,也有可能导致大量的请求访问db,所以需要等所有操作做完再释放
     *
     * @return
     */
    public List queryDb() {
        synchronized (this) {
            //拿到锁之后,再判断缓存中是有,如果没有,再去查db
            List<Object> list = redisCache.getCacheList("testKey");
            if (!list.isEmpty()) {
                //如果不为空,直接返回
                List returnList = queryDb();
                return returnList;
            }
            //如果还是没有,查询db
            List<String> queryList = new ArrayList<>();
            //放入缓存
            redisCache.setCacheList("testKey", queryList);
            return queryList;
        }
    }
}
