package com.thinker.cloud.auth.core.member;

import com.thinker.cloud.auth.core.support.password.PasswordAuthenticationProvider;
import com.thinker.cloud.auth.core.support.sms.SmsAuthenticationProvider;
import com.thinker.cloud.auth.core.userdetails.MemberUserDetailsService;
import com.thinker.cloud.security.repository.RedisRegisteredClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.ClientSecretAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthExtendUtils;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 会员登录安全配置入口
 *
 * @author admin
 **/
@Component
@RequiredArgsConstructor
public class MemberSecurityConfigurer extends AbstractHttpConfigurer<MemberSecurityConfigurer, HttpSecurity> {

    private final PasswordEncoder passwordEncoder;
    private final OAuth2AuthorizationService authorizationService;
    private final MemberUserDetailsService memberUserDetailsService;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final RedisRegisteredClientRepository redisRegisteredClientRepository;

    @Override
    public void init(HttpSecurity http) {
        // 初始化默认授权登录认证转换器
        this.initDefaultAuthenticationProviders(http);
    }

    @Override
    public void configure(HttpSecurity http) {
        MemberAuthenticationFilter memberAuthenticationFilter = new MemberAuthenticationFilter(http);
        memberAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        memberAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        http.addFilterAfter(memberAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 初始化默认授权登录认证转换器
     *
     * @param http http
     */
    private void initDefaultAuthenticationProviders(HttpSecurity http) {

        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = OAuth2AuthExtendUtils.getTokenGenerator(http);

        // 客户端
        ClientSecretAuthenticationProvider clientSecretAuthenticationProvider = new ClientSecretAuthenticationProvider(
                redisRegisteredClientRepository, authorizationService);
        clientSecretAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        http.authenticationProvider(clientSecretAuthenticationProvider);

        // 账号密码
        PasswordAuthenticationProvider passwordAuthenticationProvider = new PasswordAuthenticationProvider(passwordEncoder
                , authorizationService, tokenGenerator);
        http.authenticationProvider(passwordAuthenticationProvider);

        // 手机短信
        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider(authorizationService, tokenGenerator);
        http.authenticationProvider(smsAuthenticationProvider);
    }
}
