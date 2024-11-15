package com.thinker.cloud.auth.core.support.password;

import com.thinker.cloud.auth.core.support.base.BaseAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * 密码登录认证转换器
 *
 * @author admin
 **/
public class PasswordAuthenticationToken extends BaseAuthenticationToken {

    public static final AuthorizationGrantType GRANT_TYPE = new AuthorizationGrantType("password");

    private final String credentials;

    public PasswordAuthenticationToken(String username, String password) {
        super(GRANT_TYPE, username);
        this.credentials = password;
    }

    public PasswordAuthenticationToken(AuthorizationGrantType grantType, UserDetails principal) {
        super(grantType, principal, principal.getAuthorities());
        this.credentials = principal.getPassword();
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }
}
