package com.thinker.cloud.auth.core.support.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 自定义授权模式抽象
 *
 * @author admin
 */
@Getter
@Setter
public abstract class BaseAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final AuthorizationGrantType grantType;
    private Set<String> scopes = Collections.emptySet();
    private Map<String, Object> additionalParameters = Collections.emptyMap();

    public BaseAuthenticationToken(AuthorizationGrantType grantType, Object principal) {
        super(Collections.emptyList());
        Assert.notNull(grantType, "grantType cannot be null");
        Assert.notNull(principal, "principal cannot be null");
        this.grantType = grantType;
        this.principal = principal;
    }

    public BaseAuthenticationToken(AuthorizationGrantType grantType, Object principal,
                                   Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        Assert.notNull(grantType, "grantType cannot be null");
        Assert.notNull(principal, "principal cannot be null");
        this.grantType = grantType;
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
