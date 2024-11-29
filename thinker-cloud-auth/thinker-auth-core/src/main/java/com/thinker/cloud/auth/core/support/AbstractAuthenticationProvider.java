package com.thinker.cloud.auth.core.support;

import com.google.common.collect.Maps;
import com.thinker.cloud.auth.core.userdetails.BaseUserDetailsService;
import com.thinker.cloud.common.constants.CommonConstants;
import com.thinker.cloud.common.exception.AbstractException;
import com.thinker.cloud.core.utils.tenant.TenantContextHolder;
import com.thinker.cloud.security.constants.OAuth2ErrorCodesExpand;
import com.thinker.cloud.security.exception.ScopeException;
import com.thinker.cloud.security.token.AbstractAuthenticationToken;
import com.thinker.cloud.security.utils.SecurityMessageSourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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

        // 获取客户端信息
        OAuth2ClientAuthenticationToken clientPrincipal = getAuthenticatedClient(authenticationToken);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        Assert.notNull(registeredClient, "Registered client cannot be null");

        try {
            // 设置租户id
            Optional.ofNullable(registeredClient.getClientSettings())
                    .map(client -> client.getSetting(CommonConstants.TENANT))
                    .ifPresent(tenantId -> TenantContextHolder.setTenantId((Long) tenantId));

            // 登录授权验证身份主体逻辑
            AbstractAuthenticationToken principal = this.authenticationPrincipal(authentication);
            principal.setDetails(authenticationToken.getDetails());
            principal.setScopes(getAuthorizedScopes(authenticationToken, registeredClient));
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
                    .withRegisteredClient(Objects.requireNonNull(registeredClient))
                    .principalName(principal.getName())
                    .attribute(Principal.class.getName(), principal)
                    .authorizedScopes(principal.getScopes())
                    .authorizationGrantType(authenticationToken.getGrantType());

            // 客户端授权类型
            Object clientAuth = authenticationToken.getParameters(CommonConstants.AUTH_TYPE_HEADER);
            Optional.ofNullable(clientAuth).ifPresent(var -> authorizationBuilder.attribute(CommonConstants.AUTH_TYPE_HEADER, var));

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

            return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal
                    , accessToken, refreshToken, additionalParameters);
        } catch (AbstractException e) {
            TenantContextHolder.clear();
            log.error("[{}]登录授权发生业务异常 ex: {}", authenticationToken.getGrantType().getValue(), e.getMessage());
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.USERNAME_NOT_FOUND, e.getMessage(), ""));
        } catch (Exception e) {
            TenantContextHolder.clear();
            log.error("[{}]登录授权发生未知异常 ex: {}", authenticationToken.getGrantType().getValue(), e.getMessage(), e);
            throw oAuth2AuthenticationException((AuthenticationException) e);
        }
    }

    /**
     * 登录授权验证身份主体逻辑
     *
     * @param authentication authentication
     * @return T
     */
    public abstract T authenticationPrincipal(Authentication authentication);

    /**
     * 获取用户统一授权服务
     *
     * @return BaseUserDetailsService
     */
    protected BaseUserDetailsService getUserDetailsService(T authentication) {
        Object clientAuth = authentication.getParameters(CommonConstants.AUTH_TYPE_HEADER);



        return null;
    }

    ;

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
     * @param authenticationException 身份验证异常
     * @return {@link OAuth2AuthenticationException}
     */
    private OAuth2AuthenticationException oAuth2AuthenticationException(AuthenticationException authenticationException) {
        if (authenticationException instanceof UsernameNotFoundException) {
            return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.USERNAME_NOT_FOUND
                    , messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials"
                    , "Bad credentials"), ""));
        }

        if (authenticationException instanceof BadCredentialsException) {
            return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.BAD_CREDENTIALS
                    , messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials"
                    , "Bad credentials"), ""));
        }

        if (authenticationException instanceof LockedException) {
            return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.USER_LOCKED
                    , messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked"
                    , "User account is locked"), ""));
        }

        if (authenticationException instanceof DisabledException) {
            return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.USER_DISABLE
                    , messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled"
                    , "User is disabled"), ""));
        }

        if (authenticationException instanceof AccountExpiredException) {
            return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.USER_EXPIRED
                    , messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired"
                    , "User account has expired"), ""));
        }

        if (authenticationException instanceof CredentialsExpiredException) {
            return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.CREDENTIALS_EXPIRED,
                    messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired"
                            , "User credentials have expired"), ""));
        }

        if (authenticationException instanceof ScopeException) {
            return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_SCOPE
                    , messages.getMessage("AbstractAccessDecisionManager.accessDenied"
                    , "invalid_scope"), ""));
        }

        return new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodesExpand.UN_KNOW_LOGIN_ERROR
                , messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials"), ""));
    }

    /**
     * 获取客户端信息
     *
     * @param authenticationToken authenticationToken
     * @return OAuth2ClientAuthenticationToken
     */
    private static OAuth2ClientAuthenticationToken getAuthenticatedClient(AbstractAuthenticationToken authenticationToken) {
        OAuth2ClientAuthenticationToken clientPrincipal = getClientPrincipal();
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        if (Objects.isNull(registeredClient)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }

        // 检查客户端是否支持此授权模式
        if (!registeredClient.getAuthorizationGrantTypes().contains(authenticationToken.getGrantType())) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }
        return clientPrincipal;
    }

    /**
     * 获取客户端信息
     *
     * @return OAuth2ClientAuthenticationToken
     */
    private static OAuth2ClientAuthenticationToken getClientPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || !(authentication instanceof OAuth2ClientAuthenticationToken clientPrincipal)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
        }

        if (!clientPrincipal.isAuthenticated()) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
        }
        return clientPrincipal;
    }
}
