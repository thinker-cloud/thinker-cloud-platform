package com.thinker.cloud.auth.core.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinker.cloud.auth.core.converters.AccessTokenResponseHttpMessageConverter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * 登录成功事件处理
 *
 * @author admin
 */
@Slf4j
@Component
@AllArgsConstructor
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @SneakyThrows
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("用户：{} 登录成功", authentication.getName());
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;

        // 访问令牌
        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
                .tokenType(accessToken.getTokenType()).scopes(accessToken.getScopes());

        // 令牌有效期
        if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
            builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
        }

        // 刷新令牌
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
        if (refreshToken != null) {
            builder.refreshToken(refreshToken.getTokenValue());
        }

        // 其他参数
        Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();
        if (!CollectionUtils.isEmpty(additionalParameters)) {
            builder.additionalParameters(additionalParameters);
        }

        OAuth2AccessTokenResponse accessTokenResponse = builder.build();
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        AccessTokenResponseHttpMessageConverter messageConverter = new AccessTokenResponseHttpMessageConverter(objectMapper);
        messageConverter.write(accessTokenResponse, MediaType.APPLICATION_JSON, httpResponse);
    }
}
