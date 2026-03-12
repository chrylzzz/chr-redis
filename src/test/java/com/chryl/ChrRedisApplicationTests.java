package com.chryl;

import com.alibaba.fastjson2.JSONObject;
import com.chryl.po.GoodsPo;
import com.chryl.po.OrderPo;
import com.chryl.redis.RedisCache;
import com.chryl.redis.general.GeneralRedis;
import com.chryl.redis.list.ListService;
import com.chryl.redis.string.StringService;
import com.chryl.utils.SpringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
public class ChrRedisApplicationTests {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void contextLoads() {
        String key = "1230";
        Map<String, String> cacheMap = new HashMap<>(2);
        cacheMap.put("ccz", "wm2");
        cacheMap.put("cczt", "wma");
        SpringUtils.getBean(RedisCache.class).setCacheMap(getCacheKey(key), cacheMap);


        Map<String, Object> map = SpringUtils.getBean(RedisCache.class).getCacheMap(getCacheKey(key));
        System.out.println(map);
        boolean b = SpringUtils.getBean(RedisCache.class).deleteObject(getCacheKey(key));
        System.out.println(b);
        Map<String, Object> mapz = SpringUtils.getBean(RedisCache.class).getCacheMap(getCacheKey(key));
        System.out.println(mapz);
    }

    @Test
    public void contextLoads1() {
    }


    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    public static String getCacheKey(String configKey) {
        return "sys_test:" + configKey;
    }


    //========================测试redis数据类型========================
//    List<String> strRed = Arrays.asList("1991", "1992", "1993", "1994", "1995", "1996");
    @Test
    public void contextLoads2() {//list
        List<String> strRed = Arrays.asList("1991", "1992", "1993", "1994", "1995", "1996", "1991", "1992");
        long strRed1 = SpringUtils.getBean(RedisCache.class).setCacheList("strRed", strRed);
        System.out.println(strRed1);

        List<String> strRed2 = SpringUtils.getBean(RedisCache.class).getCacheList("strRed");
        System.out.println(strRed2);
    }

    /*
    @Test
    public void contextLoads3() {
//        List<Integer> strRed = Arrays.asList(1991, 1992, 1993, 1994, 1995, 1996, 1991, 1992);
//        Set<Integer> hashSet = new HashSet<>(strRed);

        List<String> strRed = Arrays.asList("1991", "1992", "1993", "1994", "1995", "1996", "1991", "1992");
        Set<String> hashSet = new HashSet<>(strRed);

        System.out.println(hashSet);
        long strRed1 = SpringUtils.getBean(RedisCache.class).setCacheSet("strRedSet", hashSet);
        System.out.println(strRed1);
    Set<String> strRedSet = redisCache.getCacheSet("strRedSet");

    }
    */

    @Test
    public void contextLoads4() {//map

        Map<String, Integer> a = new HashMap<>();
        a.put("zz", 10992);
        a.put("kk2", 1992);
        a.put("z2z", 192);
        a.put("z4z", 109);
        a.put("z1z", 192);
        redisCache.setCacheMap("redMap", a);


        Map<String, Object> redMap = redisCache.getCacheMap("redMap");
        System.out.println(redMap);
    }


    @Test
    public void contextLoads5() {//hash

        List<Integer> strRed = Arrays.asList(1991, 1992, 1993, 2);
        redisCache.setCacheMapValue("redStrKey", "strHashKey", strRed);
        redisCache.setCacheMapValue("redStrKey", "strHashKey2", strRed);

        List<Integer> strRed2 = redisCache.getCacheMapValue("redStrKey", "strHashKey");
        System.out.println(strRed2);
    }


    @Test
    public void show() {
        OrderPo orderPo = new OrderPo();
        orderPo.setOrderId(21393993493L);
        orderPo.setUserId(28384393221391L);
        orderPo.setOrderPrice(new BigDecimal(992.29).setScale(2, BigDecimal.ROUND_HALF_UP));
        GoodsPo goodsPo1 = new GoodsPo(123243242L, "dami", new BigDecimal(7732.11).setScale(2, BigDecimal.ROUND_HALF_UP), 2);
        GoodsPo goodsPo2 = new GoodsPo(284838932L, "xiaomi", new BigDecimal(7732.11).setScale(2, BigDecimal.ROUND_HALF_UP), 2);
        GoodsPo goodsPo3 = new GoodsPo(3838384L, "heidou", new BigDecimal(7732.11).setScale(2, BigDecimal.ROUND_HALF_UP), 2);
        List<GoodsPo> goodsPoList = new ArrayList<>();
        goodsPoList.add(goodsPo1);
        goodsPoList.add(goodsPo2);
        goodsPoList.add(goodsPo3);
        orderPo.setGoodsPoList(goodsPoList);
        redisCache.setCacheObject("20201020order", orderPo);
        OrderPo cacheObject = (OrderPo) redisCache.getCacheObject("20201020order");
        System.out.println(cacheObject);
    }

    @Autowired
    private ListService listService;
    @Autowired
    private GeneralRedis generalRedis;
    @Resource
    private StringService stringService;

    @Test
    public void setPop() {
        for (int i = 0; i < 10; i++) {
            String key = "yjw";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("know接收到请求的时间", LocalDateTime.now().toString());
            jsonObject.put("know发送kernel请求的时间", LocalDateTime.now().toString());
            jsonObject.put("know接第一个字的时间", LocalDateTime.now().toString());
            jsonObject.put("know接最后一个字的时间", LocalDateTime.now().toString());
            jsonObject.put("know本次请求总耗时", LocalDateTime.now().toString());
            jsonObject.put("本次请求返回总字数", LocalDateTime.now().toString());
            listService.leftPush(key, jsonObject.toString());
            generalRedis.expire(key, 360);
        }
        String res = "设置成功";
        System.out.println(res);

    }
    @Test
    public void leftPop() {
        List<String> list = redisTemplate.opsForList().leftPop("yjw", 30);
        System.out.println(list);
    }

}
