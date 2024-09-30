package com.thinker.cloud.auth.support.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 自定义授权模式抽象
 *
 * @author admin
 */
@Getter
@Setter
public abstract class BaseOauth2AuthenticationToken extends AbstractAuthenticationToken {

    private final AuthorizationGrantType grantType;
    private Authentication clientPrincipal;
    private Map<String, Object> additionalParameters;
    private Set<String> scopes;

    public BaseOauth2AuthenticationToken(AuthorizationGrantType grantType) {
        super(Collections.emptyList());
        Assert.notNull(grantType, "grantType cannot be null");
        this.grantType = grantType;
    }

    public BaseOauth2AuthenticationToken(AuthorizationGrantType grantType, Authentication clientPrincipal,
                                         @Nullable Set<String> scopes, @Nullable Map<String, Object> additionalParameters) {
        super(Collections.emptyList());
        Assert.notNull(grantType, "grantType cannot be null");
        Assert.notNull(clientPrincipal, "clientPrincipal cannot be null");
        this.grantType = grantType;
        this.clientPrincipal = clientPrincipal;
        this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
        this.additionalParameters = Collections.unmodifiableMap(additionalParameters != null
                ? new HashMap<>(additionalParameters) : Collections.emptyMap());
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return this.clientPrincipal;
    }
}
