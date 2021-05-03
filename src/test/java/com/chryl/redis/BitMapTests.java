package com.chryl.redis;

import com.chryl.redis.bitmap.BitMapService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BitMapTests {
    @Resource
    private BitMapService bitMapService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void bitSet() {
        bitMapService.bitSet("112", 6L, true);
        bitMapService.bitSet("112", 0, true);

        log.info("" + bitMapService.bitGet("112", 6L));
        log.info("" + bitMapService.bitGet("112", 0L));
        log.info("" + bitMapService.bitGet("112", 3L));
    }

    @Test
    public void bitGet() {
        log.info("bitGet:{}", bitMapService.bitGet("小明", 7));
        log.info("bitGet:{}", bitMapService.bitGet("小明", 1));
    }

    @Test
    public void bitCount() {
        log.info("bitCount:{}", bitMapService.bitCount("小明", 0, -1));
    }

    @Test
    public void bitOpAnd() {
        bitMapService.bitSet("aaa", 1, true);
        bitMapService.bitSet("bbb", 1, true);
        bitMapService.bitSet("bbb", 6, true);
        log.info("bitOpAnd:{}", bitMapService.bitOpAnd("javaBitOpAnd", "aaa", "bbb"));
        log.info("bitCount:{}", bitMapService.bitCount("javaBitOpAnd", 0, -1));
    }

    @Test
    public void bitOpOr() {
        bitMapService.bitSet("aaa", 1, true);
        bitMapService.bitSet("bbb", 1, true);
        bitMapService.bitSet("bbb", 6, true);
        log.info("bitOpOr:{}", bitMapService.bitOpOr("javaBitOpOr", "aaa", "bbb"));
        log.info("bitCount:{}", bitMapService.bitCount("javaBitOpOr", 0, -1));
    }

    @Test
    public void bitOpXor() {
        bitMapService.bitSet("aaa", 1, true);
        bitMapService.bitSet("bbb", 1, true);
        bitMapService.bitSet("bbb", 6, true);
        log.info("bitOpXor:{}", bitMapService.bitOpXor("javaBitOpXor", "aaa", "bbb"));
        log.info("bitCount:{}", bitMapService.bitCount("javaBitOpXor", 0, -1));
    }


    //////////////////////Chryl
    @Test
    public void show() {
        bitMapService.bitSet("20210406", 1002943200001L, true);
        bitMapService.bitSet("20210406", 1003432200004L, true);
        bitMapService.bitSet("20210406", 1002432200001L, true);
        bitMapService.bitSet("20210406", 1004322000001L, true);
        bitMapService.bitSet("20210406", 1004004000001L, true);


        log.info("" + bitMapService.bitGet("20210406", 100400441L));
    }
}
