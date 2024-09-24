package com.thinker.cloud.upms;

import com.thinker.cloud.security.annotation.EnableResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户权限管理中心
 *
 * @author xfy
 * @since 2024-09-19 17:52
 **/
@EnableResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class ThinkerUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThinkerUpmsApplication.class, args);
    }

}
