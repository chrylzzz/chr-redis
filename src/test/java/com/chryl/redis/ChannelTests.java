package com.chryl.redis;

import com.chryl.redis.channel.ChannelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ChannelTests {
    @Resource
    private ChannelService channelService;

    @Test
    public void publishMessage() {
        channelService.publishMessage("aa", "javaMessage");
    }
}
