package com.thinker.cloud.auth.core.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.BooleanUtil;
import com.thinker.cloud.auth.api.model.vo.OauthClientVO;
import com.thinker.cloud.security.constants.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

/**
 * 客户端相关操作
 *
 * @author admin
 **/
@Service
@RequiredArgsConstructor
public class RegisteredOauthClientRepository implements RegisteredClientRepository {

    private final IOauthClientRepository oauthClientRepository;

    /**
     * 刷新令牌有效期默认 30 天
     */
    private final static int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 30;

    /**
     * 请求令牌有效期默认 12 小时
     */
    private final static int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 12;

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        // 获取客户端详情
        OauthClientVO clientDetails = oauthClientRepository.loadClientByClientId(clientId);
        Optional.ofNullable(clientDetails).orElseThrow(() -> new OAuth2AuthorizationCodeRequestAuthenticationException(
                new OAuth2Error("客户端异常"), null));

        RegisteredClient.Builder builder = RegisteredClient.withId(clientDetails.getClientId())
                .clientId(clientDetails.getClientId())
                .clientSecret(SecurityConstants.SECURITY_NOOP + clientDetails.getClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);

        // 授权类型
        Arrays.stream(clientDetails.getGrantTypes())
                .map(AuthorizationGrantType::new)
                .forEach(builder::authorizationGrantType);

        // 授权范围
        builder.scopes(scopes -> {
            String scope = clientDetails.getScope();
            Set<String> scopeSet = Convert.toSet(String.class, scope);
            scopes.addAll(scopeSet);
        });

        // 回调地址
        builder.redirectUris(uris -> {
            String redirectUri = clientDetails.getRedirectUri();
            Set<String> redirectUris = Convert.toSet(String.class, redirectUri);
            uris.addAll(redirectUris);
        });

        return builder.tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                        .accessTokenTimeToLive(Duration.ofSeconds(
                                Optional.ofNullable(clientDetails.getAccessTokenValidity())
                                        .orElse(ACCESS_TOKEN_VALIDITY_SECONDS)))
                        .refreshTokenTimeToLive(Duration.ofSeconds(
                                Optional.ofNullable(clientDetails.getRefreshTokenValidity())
                                        .orElse(REFRESH_TOKEN_VALIDITY_SECONDS)))
                        .build())
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(!BooleanUtil.toBoolean(clientDetails.getAutoApprove()))
                        .build())
                .build();
    }
}
