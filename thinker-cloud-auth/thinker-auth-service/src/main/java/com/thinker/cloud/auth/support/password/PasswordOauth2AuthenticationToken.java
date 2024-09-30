package com.thinker.cloud.auth.support.password;

import com.thinker.cloud.auth.support.base.BaseOauth2AuthenticationToken;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
import java.util.Set;

/**
 * 密码登录认证转换器
 *
 * @author admin
 **/
public class PasswordOauth2AuthenticationToken extends BaseOauth2AuthenticationToken {

    public static final AuthorizationGrantType GRANT_TYPE = new AuthorizationGrantType("password");

    public PasswordOauth2AuthenticationToken(String username, String password) {
        super(GRANT_TYPE);
        super.setAuthenticated(true);
    }

    public PasswordOauth2AuthenticationToken(AuthorizationGrantType grantType, Authentication clientPrincipal,
                                             @Nullable Set<String> scopes, @Nullable Map<String, Object> additionalParameters) {
        super(grantType, clientPrincipal, scopes, additionalParameters);
    }
}
