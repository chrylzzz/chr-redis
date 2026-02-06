package com.chryl.controller.test;

import com.alibaba.fastjson2.JSONObject;
import com.chryl.redis.general.GeneralRedis;
import com.chryl.redis.list.ListService;
import com.chryl.redis.string.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by Chr.yl on 2026/1/16.
 *
 * @author Chr.yl
 */
@RestController
@RequestMapping("hy")
public class ChrylRedisController {
    @Autowired
    private ListService listService;

    @Autowired
    private GeneralRedis generalRedis;
    @Resource
    private StringService stringService;

    @GetMapping("yjw")
    public String yjw() {
        String key = "yjw";
        String val = "员嘉伟是个大帅哥~";
        listService.leftPush(key, val);
        String res = "设置成功";
        generalRedis.expire(key, 360);
        return res;
    }

    @GetMapping("yjw2")
    public String yjw2() {
        String key = "yjw";
        String val = "员嘉伟是个大帅哥~" + UUID.randomUUID();
        stringService.set(key, val);
        String res = "设置成功";
        generalRedis.expire(key, 360);
        return res;
    }

    @GetMapping("yjw3")
    public String yjw3() {
        String key = "yjw";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", "value");
        listService.leftPush(key, jsonObject.toString());
        String res = "设置成功";
        generalRedis.expire(key, 360);
        return res;
    }

    @GetMapping("getKey/{key}")
    public Object show(@PathVariable String key) {
        return listService.getAllListData(key);
    }
}
