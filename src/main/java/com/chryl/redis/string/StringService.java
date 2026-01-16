package com.chryl.redis.string;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Chr.yl on 2026/1/16.
 *
 * @author Chr.yl
 */
@Slf4j
@Component
public class StringService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }
}
