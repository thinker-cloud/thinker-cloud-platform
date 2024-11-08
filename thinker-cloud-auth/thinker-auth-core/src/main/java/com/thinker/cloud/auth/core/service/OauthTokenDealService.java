package com.thinker.cloud.auth.core.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.TemporalAccessorUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thinker.cloud.auth.api.model.vo.AccessTokenVO;
import com.thinker.cloud.auth.core.handler.AuthenticationFailureEvenHandler;
import com.thinker.cloud.core.resolver.KeyStrResolver;
import com.thinker.cloud.core.utils.ListUtil;
import com.thinker.cloud.core.utils.spring.SpringContextHolder;
import com.thinker.cloud.security.constants.OAuth2ErrorCodesExpand;
import com.thinker.cloud.security.constants.SecurityConstants;
import com.thinker.cloud.security.utils.OAuth2EndpointUtils;
import com.thinker.cloud.upms.api.sys.constants.CacheConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * token 端点相关的业务处理
 *
 * @author admin
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class OauthTokenDealService {

    private final CacheManager cacheManager;
    private final KeyStrResolver keyStrResolver;
    private final RedisTemplate<String, Object> redisTemplate;
    private final OAuth2AuthorizationService authorizationService;
    private final AuthenticationFailureEvenHandler authenticationFailureEvenHandler;
    private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter = new OAuth2AccessTokenResponseHttpMessageConverter();

    /**
     * 校验token
     *
     * @param token 令牌
     */
    public void checkToken(String token, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        if (StrUtil.isBlank(token)) {
            httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            authenticationFailureEvenHandler.onAuthenticationFailure(request, response,
                    new InvalidBearerTokenException(OAuth2ErrorCodesExpand.TOKEN_MISSING));
            return;
        }

        // 如果令牌不存在 返回401
        OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if (authorization == null || authorization.getAccessToken() == null) {
            authenticationFailureEvenHandler.onAuthenticationFailure(request, response,
                    new InvalidBearerTokenException(OAuth2ErrorCodesExpand.INVALID_BEARER_TOKEN));
            return;
        }

        Map<String, Object> claims = authorization.getAccessToken().getClaims();
        OAuth2AccessTokenResponse sendAccessTokenResponse = OAuth2EndpointUtils.sendAccessTokenResponse(authorization, claims);
        accessTokenHttpResponseConverter.write(sendAccessTokenResponse, MediaType.APPLICATION_JSON, httpResponse);
    }

    /**
     * 移除token
     *
     * @param token token
     */
    public void removeToken(String token) {
        OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if (authorization == null) {
            return;
        }

        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        if (accessToken == null || StrUtil.isBlank(accessToken.getToken().getTokenValue())) {
            return;
        }

        // 清空用户信息（立即删除）
        Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
        if (Objects.nonNull(cache)) {
            cache.evictIfPresent(authorization.getPrincipalName());
        }

        // 清空access token
        authorizationService.remove(authorization);

        // 处理自定义退出事件，保存相关日志
        SpringContextHolder.publishEvent(new LogoutSuccessEvent(new PreAuthenticatedAuthenticationToken(
                authorization.getPrincipalName(), authorization.getRegisteredClientId())));
    }

    /**
     * 查询token分页列表
     *
     * @param page     分页参数
     * @param username username
     * @return Result<Page>
     */
    public Page<AccessTokenVO> tokenPage(Page<AccessTokenVO> page, Object username) {
        String key = StrUtil.format("{}:{}"
                , SecurityConstants.TOKEN_PREFIX, OAuth2ParameterNames.ACCESS_TOKEN);
        Set<String> allKeys = redisTemplate.keys(key);
        if (Objects.isNull(allKeys)) {
            page.setTotal(0);
            page.setRecords(Collections.emptyList());
            return page;
        }

        long current = page.getCurrent(), size = page.getSize();
        List<String> pages = allKeys.stream().skip((current - 1) * size).limit(size).toList();
        List<Object> authorizations = redisTemplate.opsForValue().multiGet(pages);
        List<AccessTokenVO> list = ListUtil.toList(authorizations, obj -> {
            OAuth2Authorization authorization = (OAuth2Authorization) obj;
            AccessTokenVO tokenVo = new AccessTokenVO();
            tokenVo.setId(authorization.getId());
            tokenVo.setUsername(authorization.getPrincipalName());
            tokenVo.setClientId(authorization.getRegisteredClientId());

            OAuth2Authorization.Token<OAuth2AccessToken> auth2AccessToken = authorization.getAccessToken();
            if (Objects.nonNull(auth2AccessToken)) {
                OAuth2AccessToken accessToken = auth2AccessToken.getToken();
                tokenVo.setAccessToken(accessToken.getTokenValue());

                String expiresAt = TemporalAccessorUtil.format(accessToken.getExpiresAt(),
                        DatePattern.NORM_DATETIME_PATTERN);
                tokenVo.setExpiresAt(expiresAt);

                String issuedAt = TemporalAccessorUtil.format(accessToken.getIssuedAt(),
                        DatePattern.NORM_DATETIME_PATTERN);
                tokenVo.setIssuedAt(issuedAt);
            }
            return tokenVo;
        });

        page.setRecords(list);
        page.setTotal(allKeys.size());
        return page;
    }
}
