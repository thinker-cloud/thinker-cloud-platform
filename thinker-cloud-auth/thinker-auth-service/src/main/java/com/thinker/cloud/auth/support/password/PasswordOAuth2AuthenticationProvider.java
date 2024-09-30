package com.thinker.cloud.auth.support.password;

import com.thinker.cloud.auth.support.base.BaseOAuth2AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * 密码登录认证转换器
 *
 * @author admin
 **/
public class PasswordOAuth2AuthenticationProvider extends BaseOAuth2AuthenticationProvider<PasswordOauth2AuthenticationToken> {

    public PasswordOAuth2AuthenticationProvider(OAuth2AuthorizationService authorizationService,
                                                OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        super(authorizationService, tokenGenerator);
    }

    @Override
    public Authentication getPrincipal(Authentication authentication) {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PasswordOAuth2AuthenticationProvider.class.isAssignableFrom(authentication);
    }
}
