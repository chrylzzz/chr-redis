package com.chryl.redis;

import com.chryl.redis.channel.ChannelService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 *
 **/
@SpringBootTest
public class ChannelTests {
    @Resource
    private ChannelService channelService;

    @Test
    void publishMessage() {
        channelService.publishMessage("aa", "javaMessage");
    }
}
