package com.chryl;

import com.chryl.redis.RedisCache;
import com.chryl.utils.SpringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
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

}
