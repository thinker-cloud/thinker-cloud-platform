package com.thinker.cloud.auth.support;

import com.google.common.collect.Maps;
import com.thinker.cloud.security.constants.OAuth2ErrorCodesExpand;
import com.thinker.cloud.security.exception.ScopeException;
import com.thinker.cloud.security.utils.SecurityMessageSourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthExtendUtils;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.*;

/**
 * 处理自定义授权
 *
 * @author admin
 */
@Slf4j
public abstract class AbstractAuthenticationProvider<T extends AbstractAuthenticationToken> implements AuthenticationProvider {

    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    protected final MessageSourceAccessor messages = SecurityMessageSourceUtils.getAccessor();
    protected final AccountStatusUserDetailsChecker accountStatusChecker = new AccountStatusUserDetailsChecker();
    private static final OAuth2TokenType ID_TOKEN_TOKEN_TYPE = new OAuth2TokenType(OidcParameterNames.ID_TOKEN);


    public AbstractAuthenticationProvider(OAuth2AuthorizationService authorizationService,
                                          OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
        this.accountStatusChecker.setMessageSource(new SecurityMessageSourceUtils());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AbstractAuthenticationToken authenticationToken = (AbstractAuthenticationToken) authentication;

        OAuth2ClientAuthenticationToken clientPrincipal = OAuth2AuthExtendUtils
                .getAuthenticatedClientElseThrowInvalidClient(authenticationToken);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        if (Objects.isNull(registeredClient)) {
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR
                    , "注册客户端不能为空", ""));
        }

        try {
            // 登录验证逻辑
            T principal = this.authenticationToken(authentication);

            principal.setScopes(getAuthorizedScopes(authenticationToken, registeredClient));
            principal.setDetails(authenticationToken.getDetails());
            principal.setAdditionalParameters(authenticationToken.getAdditionalParameters());

            // @formatter:off
			DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
					.registeredClient(registeredClient)
					.principal(principal)
					.authorizationServerContext(AuthorizationServerContextHolder.getContext())
					.authorizedScopes(principal.getScopes())
					.authorizationGrantType(authenticationToken.getGrantType())
					.authorizationGrant(authenticationToken);

            // @formatter:on
            OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization
                    .withRegisteredClient(registeredClient)
                    .principalName(principal.getName())
                    .attribute(Principal.class.getName(), principal)
                    .authorizedScopes(principal.getScopes())
                    .authorizationGrantType(authenticationToken.getGrantType());

            // ----- Access token -----
            OAuth2AccessToken accessToken = this.genAccessToken(tokenContextBuilder, authorizationBuilder);

            // ----- Refresh token -----
            OAuth2RefreshToken refreshToken = this.genRefreshToken(tokenContextBuilder, authorizationBuilder, registeredClient);

            // ----- OidcId token -----
            OidcIdToken idToken = this.genOidcIdToken(tokenContextBuilder, authorizationBuilder, principal.getScopes());

            OAuth2Authorization authorization = authorizationBuilder.build();
            this.authorizationService.save(authorization);

            Map<String, Object> additionalParameters = Collections.emptyMap();
            if (idToken != null) {
                additionalParameters = Maps.newHashMap();
                additionalParameters.put(OidcParameterNames.ID_TOKEN, idToken.getTokenValue());
            }

            return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal,
                    accessToken, refreshToken, additionalParameters);
        } catch (Exception e) {
            log.error("problem in authenticate ex: {}", e.getMessage(), e);
            throw oAuth2AuthenticationException(authentication, (AuthenticationException) e);
        }
    }

    /**
     * 创建身份验证令牌对象
     *
     * @param authentication authentication
     * @return T
     */
    public abstract T authenticationToken(Authentication authentication);

    /**
     * 生成 AccessToken
     *
     * @return OAuth2AccessToken
     */
    private OAuth2AccessToken genAccessToken(DefaultOAuth2TokenContext.Builder tokenContextBuilder
            , OAuth2Authorization.Builder authorizationBuilder) {
        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR
                    , "The token generator failed to generate the access token.", ""));
        }

        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
        authorizationBuilder.id(accessToken.getTokenValue());

        if (generatedAccessToken instanceof ClaimAccessor) {
            authorizationBuilder.token(accessToken, (metadata) ->
                    metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME
                            , ((ClaimAccessor) generatedAccessToken).getClaims())
            );
        } else {
            authorizationBuilder.accessToken(accessToken);
        }
        return accessToken;
    }

    /**
     * 生成 RefreshToken
     *
     * @return OAuth2RefreshToken
     */
    private OAuth2RefreshToken genRefreshToken(DefaultOAuth2TokenContext.Builder tokenContextBuilder
            , OAuth2Authorization.Builder authorizationBuilder, RegisteredClient registeredClient) {
        // Do not issue refresh token to public client
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN)) {
            OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
            if (generatedRefreshToken != null) {
                if (!(generatedRefreshToken instanceof OAuth2RefreshToken refreshToken)) {
                    throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR
                            , "The token generator failed to generate the refresh token.", ""));
                }
                authorizationBuilder.refreshToken(refreshToken);
                return refreshToken;
            }
        }
        return null;
    }

    /**
     * 生成 OidcIdToken
     *
     * @return OidcIdToken
     */
    private OidcIdToken genOidcIdToken(DefaultOAuth2TokenContext.Builder tokenContextBuilder
            , OAuth2Authorization.Builder authorizationBuilder, Set<String> authorizedScopes) {
        if (authorizedScopes.contains(OidcScopes.OPENID)) {
            // @formatter:off
            OAuth2TokenContext tokenContext = tokenContextBuilder
                    .tokenType(ID_TOKEN_TOKEN_TYPE)
                    // ID token customizer may need access to the access token and/or refresh token
                    .authorization(authorizationBuilder.build())
                    .build();

            // @formatter:on
            OAuth2Token generatedIdToken = this.tokenGenerator.generate(tokenContext);
            if (!(generatedIdToken instanceof ClaimAccessor)) {
                throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the ID token.", ""));
            }

            OidcIdToken oidcIdToken = new OidcIdToken(generatedIdToken.getTokenValue(), generatedIdToken.getIssuedAt(),
                    generatedIdToken.getExpiresAt(), ((ClaimAccessor) generatedIdToken).getClaims());
            authorizationBuilder.token(oidcIdToken, (metadata) ->
                    metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, oidcIdToken.getClaims()));
            return oidcIdToken;
        }
        return null;
    }

    /**
     * 获取授权范围
     *
     * @param authenticationToken authenticationToken
     * @param registeredClient    registeredClient
     * @return Set<String>
     */
    private static Set<String> getAuthorizedScopes(AbstractAuthenticationToken authenticationToken,
                                                   RegisteredClient registeredClient) {
        // Default to configured scopes
        if (CollectionUtils.isEmpty(authenticationToken.getScopes())) {
            return new LinkedHashSet<>();
        }

        // 检查客户端授权范围
        if (!registeredClient.getScopes().containsAll(authenticationToken.getScopes())) {
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_SCOPE,
                    "OAuth 2.0 参数: " + OAuth2ParameterNames.SCOPE, null));
        }

        // 检查客户端授权类型
        if (!registeredClient.getAuthorizationGrantTypes().contains(authenticationToken.getGrantType())) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }

        return new LinkedHashSet<>(authenticationToken.getScopes());
    }

    /**
     * 登录异常转换为oauth2异常
     *
     * @param authentication          身份验证
     * @param authenticationException 身份验证异常
     * @return {@link OAuth2AuthenticationException}
     */
    private OAuth2AuthenticationException oAuth2AuthenticationException(Authentication authentication,
                                                                        AuthenticationException authenticationException) {
        if (authenticationException instanceof UsernameNotFoundException) {
            return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.USERNAME_NOT_FOUND
                    , messages.getMessage("JdbcDaoImpl.notFound"
                    , new Object[]{authentication.getName()}
                    , "Username {0} not found"), ""));
        }

        if (authenticationException instanceof BadCredentialsException) {
            return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.BAD_CREDENTIALS
                    , this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials"
                    , "Bad credentials"), ""));
        }

        if (authenticationException instanceof LockedException) {
            return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.USER_LOCKED
                    , this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked"
                    , "User account is locked"), ""));
        }

        if (authenticationException instanceof DisabledException) {
            return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.USER_DISABLE
                    , this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled"
                    , "User is disabled"), ""));
        }

        if (authenticationException instanceof AccountExpiredException) {
            return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.USER_EXPIRED
                    , this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired"
                    , "User account has expired"), ""));
        }

        if (authenticationException instanceof CredentialsExpiredException) {
            return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.CREDENTIALS_EXPIRED,
                    this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired"
                            , "User credentials have expired"), ""));
        }

        if (authenticationException instanceof ScopeException) {
            return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_SCOPE
                    , this.messages.getMessage("AbstractAccessDecisionManager.accessDenied"
                    , "invalid_scope"), ""));
        }

        return new OAuth2AuthenticationException(OAuth2ErrorCodesExpand.UN_KNOW_LOGIN_ERROR);
    }
}
