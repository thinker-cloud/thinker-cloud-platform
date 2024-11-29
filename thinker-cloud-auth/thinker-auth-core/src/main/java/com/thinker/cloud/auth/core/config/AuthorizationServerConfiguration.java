package com.thinker.cloud.auth.core.config;

import com.thinker.cloud.auth.core.handler.Oauth2AuthenticationFailureHandler;
import com.thinker.cloud.auth.core.handler.Oauth2AuthenticationSuccessHandler;
import com.thinker.cloud.auth.core.support.password.PasswordAuthenticationConverter;
import com.thinker.cloud.auth.core.support.password.PasswordAuthenticationProvider;
import com.thinker.cloud.auth.core.support.sms.SmsAuthenticationConverter;
import com.thinker.cloud.auth.core.support.sms.SmsAuthenticationProvider;
import com.thinker.cloud.security.component.*;
import com.thinker.cloud.security.properties.SecurityProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthExtendUtils;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2AccessTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.DelegatingAuthenticationConverter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * 授权服务器配置
 *
 * @author admin
 **/
@Configuration
@AllArgsConstructor
public class AuthorizationServerConfiguration {

    private final PasswordEncoder passwordEncoder;
    private final SecurityProperties securityProperties;
    private final PermitAllUrlMatcher permitAllUrlMatcher;
    private final BearerTokenExtractor bearerTokenExtractor;
    private final AuthAccessDeniedHandler authAccessDeniedHandler;
    private final Oauth2AuthExceptionEntryPoint authExceptionEntryPoint;
    private final AuthorizationServiceIntrospector authorizationServiceIntrospector;

    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenEnhanceCustomizer oAuth2TokenEnhanceCustomizer;
    private final Oauth2AuthenticationFailureHandler authenticationFailureEvenHandler;
    private final Oauth2AuthenticationSuccessHandler authenticationSuccessEventHandler;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        // 授权配置
        return http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(permitAllUrlMatcher)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .opaqueToken(token -> token.introspector(authorizationServiceIntrospector))
                        // 身份验证入口点
                        .authenticationEntryPoint(authExceptionEntryPoint)
                        .bearerTokenResolver(bearerTokenExtractor))
                .with(authorizationServerConfigurer
                        // 认证授权端点
                        .tokenEndpoint(tokenEndpoint -> tokenEndpoint
                                // 自定义授权认证转换器
                                .accessTokenRequestConverter(this.createAccessTokenRequestConverter())
                                // 自定义授权认证模式
                                .authenticationProviders(this.createAuthenticationProviders(http))
                                // 登录成功处理器
                                .accessTokenResponseHandler(authenticationSuccessEventHandler)
                                // 登录失败处理器
                                .errorResponseHandler(authenticationFailureEvenHandler))
                        // 开启OpenID Connect 1.0协议相关端点
                        .oidc(Customizer.withDefaults())
                        // 授权码端点个性化confirm页面
                        .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                                .errorResponseHandler(authenticationFailureEvenHandler)
                                .consentPage("/token/confirm_access")), Customizer.withDefaults())
                .with(authorizationServerConfigurer
                        // 客户端认证
                        .clientAuthentication(clientAuthentication -> clientAuthentication
                                .errorResponseHandler(authenticationFailureEvenHandler))
                        // 授权服务信息配置
                        .authorizationServerSettings(AuthorizationServerSettings.builder()
                                .issuer(securityProperties.getIssuer()).build())
                        // 存储token的实现
                        .authorizationService(authorizationService), Customizer.withDefaults())
                .exceptionHandling(handler -> handler
                        // 访问被拒绝处理程序
                        .accessDeniedHandler(authAccessDeniedHandler)
                        // 身份验证入口点
                        .authenticationEntryPoint(authExceptionEntryPoint))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    /**
     * 令牌生成规则实现
     *
     * @return OAuth2TokenGenerator
     */
    @Bean
    public OAuth2TokenGenerator<OAuth2Token> oAuth2TokenGenerator() {
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        // 注入Token 增加关联用户信息
        accessTokenGenerator.setAccessTokenCustomizer(oAuth2TokenEnhanceCustomizer);
        return new DelegatingOAuth2TokenGenerator(accessTokenGenerator, new OAuth2RefreshTokenGenerator());
    }

    /**
     * 创建自定义授权认证转换器
     *
     * @return AuthenticationConverter
     */
    private AuthenticationConverter createAccessTokenRequestConverter() {
        return new DelegatingAuthenticationConverter(Arrays.asList(
                new PasswordAuthenticationConverter(),
                new SmsAuthenticationConverter()));
    }

    /**
     * 创建自定义授权认证类型提供者
     *
     * @return AuthenticationConverter
     */
    private Consumer<List<AuthenticationProvider>> createAuthenticationProviders(HttpSecurity http) {
        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = OAuth2AuthExtendUtils.getTokenGenerator(http);
        return providers -> {
            // 账号密码
            PasswordAuthenticationProvider passwordAuthenticationProvider =
                    new PasswordAuthenticationProvider(passwordEncoder, authorizationService, tokenGenerator);
            providers.add(passwordAuthenticationProvider);

            // 手机短信
            SmsAuthenticationProvider smsAuthenticationProvider =
                    new SmsAuthenticationProvider(authorizationService, tokenGenerator);
            providers.add(smsAuthenticationProvider);
        };
    }
}
