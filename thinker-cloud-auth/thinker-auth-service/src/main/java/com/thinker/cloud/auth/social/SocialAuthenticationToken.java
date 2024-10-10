package com.thinker.cloud.auth.social;

import com.thinker.cloud.auth.support.AbstractAuthenticationToken;
import com.thinker.cloud.upms.api.uac.model.AuthParams;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Collection;

/**
 * 社交授权验证Token信息
 *
 * @author admin
 **/
public class SocialAuthenticationToken extends AbstractAuthenticationToken {

    public SocialAuthenticationToken(AuthParams authParams) {
        super(new AuthorizationGrantType(authParams.getAuthType()), authParams);
    }

    public SocialAuthenticationToken(AuthorizationGrantType grantType, Object principal,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(grantType, principal, authorities);
    }
}
