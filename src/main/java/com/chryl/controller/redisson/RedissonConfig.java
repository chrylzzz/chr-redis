package com.chryl.controller.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Chr.yl on 2021/5/3.
 *
 * @author Chr.yl
 */
@Configuration
public class RedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        Config config = new Config();

//        config.useClusterServers().addNodeAddress("127.0.0.1:6379");
//        config.useSingleServer().setAddress("rediss://127.0.0.1:6379");//安全连接

//        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        config.useSingleServer().setAddress("redis://10.40.137.101:9020").setDatabase(0).setPassword("b3mr~N_ptFDx");

        return Redisson.create(config);
    }
}
