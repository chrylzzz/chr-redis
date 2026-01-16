package com.chryl.controller.test;

import com.chryl.redis.general.GeneralRedis;
import com.chryl.redis.list.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("yjw")
    public String yjw() {
        String key = "yjw";
        String val = "员嘉伟是个大帅哥~";
        listService.leftPush(key, val);
        String res = "设置成功";
        generalRedis.expire(key, 360);
        return res;
    }


}
