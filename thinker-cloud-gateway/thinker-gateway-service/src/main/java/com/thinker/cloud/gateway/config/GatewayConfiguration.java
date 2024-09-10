package com.thinker.cloud.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinker.cloud.gateway.filter.GlobalRequestGlobalFilter;
import com.thinker.cloud.gateway.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 网关配置
 *
 * @author admin
 */
@Configuration(proxyBeanMethods = false)
public class GatewayConfiguration {

    /**
     * 创建全局过滤器
     *
     * @return PigRequest全局过滤器
     */
    @Bean
    public GlobalRequestGlobalFilter pigRequestGlobalFilter() {
        return new GlobalRequestGlobalFilter();
    }

    /**
     * 创建全局异常处理程序
     *
     * @param objectMapper 对象映射器
     * @return 全局异常处理程序
     */
    @Bean
    public GlobalExceptionHandler globalExceptionHandler(ObjectMapper objectMapper) {
        return new GlobalExceptionHandler(objectMapper);
    }

}
