package com.chryl.controller.test;

import com.chryl.redis.general.GeneralRedis;
import com.chryl.redis.list.ListService;
import com.chryl.redis.string.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
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
public class ChrylController {
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
}
