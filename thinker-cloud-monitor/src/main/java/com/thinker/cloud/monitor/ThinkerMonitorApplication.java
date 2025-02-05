package com.thinker.cloud.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 监控中心
 *
 * @author admin
 **/
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class ThinkerMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThinkerMonitorApplication.class, args);
    }

}
