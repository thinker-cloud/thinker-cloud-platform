package com.thinker.cloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 认证授权中心
 *
 * @author admin
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class ThinkerAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThinkerAuthApplication.class, args);
    }

}
