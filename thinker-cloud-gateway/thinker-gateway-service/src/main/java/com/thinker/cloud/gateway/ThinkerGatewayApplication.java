package com.thinker.cloud.gateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API网关
 *
 * @author admin
 **/
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
public class ThinkerGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThinkerGatewayApplication.class, args);
    }
}
