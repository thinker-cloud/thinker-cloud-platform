package com.thinker.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 分库分表demo启动类
 *
 * @author admin
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class ShardSphereDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardSphereDemoApplication.class, args);
    }

}
