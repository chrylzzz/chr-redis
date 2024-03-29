package com.chryl.redis;

import com.alibaba.fastjson.JSON;
import com.chryl.redis.set.SetService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SetTests {

    @Resource
    private SetService setService;

    @Test
    public void add() {
        setService.add("javaSet5", "1111");
        setService.add("javaSet5", "1111");
        setService.add("javaSet5", "1111");
        setService.add("javaSet5", "1111");
        setService.add("javaSet5", "1111");
        setService.add("javaSet5", "3333");
        setService.add("javaSet5", "3333");
    }

    @Test
    public void size() {
        log.info(String.valueOf(setService.size("javaSet1")));
    }

    @Test
    public void members() {
        log.info(JSON.toJSONString(setService.members("javaSet2")));
    }

    @Test
    public void remove() {
        log.info(JSON.toJSONString(setService.remove("javaSet4", "1111")));
    }

    @Test
    public void differenceAndStore() {
        Collection<String> keys = new ArrayList<>();
        log.info(String.valueOf(setService.differenceAndStore("javaSet3", keys, "javaSet2")));
    }

    @Test
    public void intersectAndStore() {
        List<String> keys = new ArrayList<>();
        //keys.add("javaSet1");
        log.info(String.valueOf(setService.intersectAndStore("javaSet", keys, "javaSet2")));
    }

    @Test
    public void unionAndStore() {
        List<String> keys = new ArrayList<>();
        keys.add("javaSet1");
        log.info(String.valueOf(setService.unionAndStore("javaSet1111", keys, "javaSetUnionAndStore")));
    }

    @Test
    public void isMember() {
        log.info(String.valueOf(setService.isMember("javaSet6", "112")));
    }

    @Test
    public void distinctRandomMembers() {
        log.info(JSON.toJSONString(setService.distinctRandomMembers("javaSet1", -1)));
//        log.info(JSON.toJSONString(setService.distinctRandomMembers("javaSet1", 2)));
//        log.info(JSON.toJSONString(setService.distinctRandomMembers("javaSet1", 2)));
//        log.info(JSON.toJSONString(setService.distinctRandomMembers("javaSet1", 2)));
//        log.info(JSON.toJSONString(setService.distinctRandomMembers("javaSet1", 2)));
    }

    @Test
    public void pop() {
        log.info(JSON.toJSONString(setService.pop("javaSet1", 1L)));
    }

    @Test
    public void randomMembers() {
        log.info(JSON.toJSONString(setService.randomMembers("javaSet1", 1L)));
    }

    @Test
    public void move() {
        log.info(JSON.toJSONString(setService.move("javaSet1", "啊的司法大厦11", "javaSet21")));
    }
}
