package com.thinker.cloud.upms;

import com.thinker.cloud.sentinel.annotation.SpringCloudApplication;
import com.thinker.cloud.security.annotation.EnableResourceServer;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;

/**
 * 用户权限管理中心
 *
 * @author xfy
 * @since 2024-09-19 17:52
 **/
@EnableDubbo
@EnableResourceServer
@SpringCloudApplication
public class ThinkerUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThinkerUpmsApplication.class, args);
    }

}
