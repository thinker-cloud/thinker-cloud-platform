package com.thinker.cloud.auth.core.config;

import com.thinker.cloud.auth.core.social.SocialSecurityConfigurer;
import com.thinker.cloud.auth.core.userdetails.AdminUserDetailsService;
import com.thinker.cloud.security.component.PermitAllUrlMatcher;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 授权服务器配置
 *
 * @author admin
 **/
@Configuration
@AllArgsConstructor
public class AuthorizationServerConfiguration {

    private final PermitAllUrlMatcher permitAllUrlMatcher;
    private final AdminUserDetailsService adminUserDetailsService;
    private final OAuth2AuthorizationService authorizationService;
    private final SocialSecurityConfigurer socialSecurityConfigurer;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(permitAllUrlMatcher)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .userDetailsService(adminUserDetailsService)
                .with(new OAuth2AuthorizationServerConfigurer()
                        // 认证授权端点
//                        .tokenEndpoint(tokenEndpoint -> tokenEndpoint
//                                // 登录成功处理器
//                                .accessTokenResponseHandler(new PigAuthenticationSuccessEventHandler())
//                                // 登录失败处理器
//                                .errorResponseHandler(new PigAuthenticationFailureEventHandler())
//                        )
                        // 客户端认证
//                        .clientAuthentication(clientAuthentication -> clientAuthentication
//                                .errorResponseHandler(new PigAuthenticationFailureEventHandler()))
                        // redis存储token的实现
                        .authorizationService(authorizationService), Customizer.withDefaults())
                .with(socialSecurityConfigurer, Customizer.withDefaults())
                .build();
    }
}
