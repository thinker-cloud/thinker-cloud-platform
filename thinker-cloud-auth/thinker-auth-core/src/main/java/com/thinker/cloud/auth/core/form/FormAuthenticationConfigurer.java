package com.thinker.cloud.auth.core.form;

import com.thinker.cloud.auth.core.handler.FormAuthenticationFailureHandler;
import com.thinker.cloud.auth.core.handler.LogoutAuthenticationSuccessHandler;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * from 表达 Security 配置
 *
 * @author admin
 **/
@AllArgsConstructor
public final class FormAuthenticationConfigurer extends AbstractHttpConfigurer<FormAuthenticationConfigurer, HttpSecurity> {

    @Override
    public void init(HttpSecurity http) throws Exception {
        http.formLogin(formLogin ->
                        formLogin.loginPage("/token/login")
                                .loginProcessingUrl("/oauth2/form")
                                .failureHandler(new FormAuthenticationFailureHandler()))
                .logout(logout ->
                        logout.logoutUrl("/oauth2/logout")
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessHandler(new LogoutAuthenticationSuccessHandler()))
                // 避免iframe同源无法登录许iframe
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .csrf(AbstractHttpConfigurer::disable);
    }

}
