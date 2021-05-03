package com.chryl.redis.channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 **/
@Component
@Slf4j
public class ChannelService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 向主题为channelName的队列中发送消息为message
     *
     * @param channelName
     * @param message
     */
    public void publishMessage(String channelName, String message) {
        stringRedisTemplate.convertAndSend(channelName, message);
    }
}
