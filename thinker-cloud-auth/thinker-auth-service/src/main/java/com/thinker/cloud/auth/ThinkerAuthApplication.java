package com.thinker.cloud.auth;

import com.thinker.cloud.sentinel.annotation.SpringCloudApplication;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;

/**
 * 认证授权中心
 *
 * @author admin
 **/
@EnableDubbo
@SpringCloudApplication
public class ThinkerAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThinkerAuthApplication.class, args);
    }

}
