package com.thinker.cloud.auth.social;

import com.thinker.cloud.auth.userdetails.AdminUserDetailsService;
import com.thinker.cloud.security.component.Oauth2AuthExceptionEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthExtendUtils;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 社交登录安全配置入口
 *
 * @author admin
 **/
@Component
@AllArgsConstructor
public class SocialSecurityConfigurer extends AbstractHttpConfigurer<SocialSecurityConfigurer, HttpSecurity> {

    private final AdminUserDetailsService adminUserDetailsService;
    private final OAuth2AuthorizationService authorizationService;
    private final AuthenticationEventPublisher authenticationEventPublisher;
    private final Oauth2AuthExceptionEntryPoint authExceptionEntryPoint;

    @Override
    public void configure(HttpSecurity http) {
        SocialAuthenticationFilter authenticationFilter = new SocialAuthenticationFilter();
        authenticationFilter.setAuthExceptionEntryPoint(authExceptionEntryPoint);
        authenticationFilter.setAuthenticationEventPublisher(authenticationEventPublisher);
        authenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        SocialAuthenticationProvider authenticationProvider = new SocialAuthenticationProvider(
                adminUserDetailsService, authorizationService, OAuth2AuthExtendUtils.getTokenGenerator(http));

        http.authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
