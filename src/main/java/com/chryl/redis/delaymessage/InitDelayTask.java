package com.chryl.redis.delaymessage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 */
@Profile(value = "redis")
@Component
public class InitDelayTask {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Bean(name = "delayTaskExecutor")
    public DelayTaskExecutor initDelayTaskExecutor() {
        DelayTaskExecutor delayTaskExecutor = new DelayTaskExecutor();
        delayTaskExecutor.initThreadPool(new ThreadPoolConfig(), stringRedisTemplate);
        return delayTaskExecutor;
    }
}
