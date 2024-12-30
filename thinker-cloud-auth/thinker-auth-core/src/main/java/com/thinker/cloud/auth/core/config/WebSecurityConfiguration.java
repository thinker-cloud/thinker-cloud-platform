package com.thinker.cloud.auth.core.config;

import com.thinker.cloud.auth.core.handler.FormAuthenticationFailureHandler;
import com.thinker.cloud.auth.core.handler.LogoutAuthenticationSuccessHandler;
import com.thinker.cloud.security.component.PermitAllUrlMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 配置
 *
 * @author admin
 **/
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final PermitAllUrlMatcher permitAllUrlMatcher;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(permitAllUrlMatcher)
                        .permitAll()
                        .requestMatchers("/token/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin(formLogin -> formLogin.loginPage("/token/login")
                        .loginProcessingUrl("/oauth2/token")
                        .failureHandler(new FormAuthenticationFailureHandler()))
                .logout(logout -> logout.logoutUrl("/token/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(new LogoutAuthenticationSuccessHandler()))
                // 避免iframe同源无法登录许iframe
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
