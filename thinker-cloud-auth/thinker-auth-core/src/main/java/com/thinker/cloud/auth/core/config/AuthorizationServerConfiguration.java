package com.thinker.cloud.auth.core.config;

import com.thinker.cloud.auth.core.handler.AuthenticationFailureEvenHandler;
import com.thinker.cloud.auth.core.handler.AuthenticationSuccessEventHandler;
import com.thinker.cloud.auth.core.social.SocialSecurityConfigurer;
import com.thinker.cloud.auth.core.userdetails.AdminUserDetailsService;
import com.thinker.cloud.security.component.OAuth2TokenEnhanceCustomizer;
import com.thinker.cloud.security.properties.ThinkerSecurityProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2AccessTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 授权服务器配置
 *
 * @author admin
 **/
@Configuration
@AllArgsConstructor
public class AuthorizationServerConfiguration {

    private final AdminUserDetailsService adminUserDetailsService;
    private final OAuth2AuthorizationService authorizationService;
    private final SocialSecurityConfigurer socialSecurityConfigurer;
    private final ThinkerSecurityProperties thinkerSecurityProperties;
    private final OAuth2TokenEnhanceCustomizer oAuth2TokenEnhanceCustomizer;
    private final AuthenticationSuccessEventHandler authenticationSuccessEventHandler;
    private final AuthenticationFailureEvenHandler authenticationFailureEvenHandler;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        // 用户信息服务
        return http.userDetailsService(adminUserDetailsService)
                // 授权配置
                .with(authorizationServerConfigurer
                        // 认证授权端点
                        .tokenEndpoint(tokenEndpoint -> tokenEndpoint
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
                                .issuer(thinkerSecurityProperties.getIssuer()).build())
                        // redis存储token的实现
                        .authorizationService(authorizationService), Customizer.withDefaults())
                .with(socialSecurityConfigurer, Customizer.withDefaults())
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
}
