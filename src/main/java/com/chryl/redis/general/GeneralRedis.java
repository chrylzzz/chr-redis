package com.chryl.redis.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * Created by Chr.yl on 2026/1/16.
 *
 * @author Chr.yl
 */
@Slf4j
@Component
public class GeneralRedis {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置超时时间
     *
     * @param key
     * @param seconds 秒
     */
    public void expire(String key, long seconds) {
        stringRedisTemplate.expire(key, Duration.ofSeconds(seconds));
    }
}
