package com.thinker.cloud.auth.social;

import com.thinker.cloud.auth.support.AbstractAuthenticationProvider;
import com.thinker.cloud.auth.userdetails.AdminUserDetailsService;
import com.thinker.cloud.core.exception.AbstractException;
import com.thinker.cloud.upms.api.uac.model.AuthParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * 社交登录认证转换器
 *
 * @author admin
 **/
@Slf4j
public class SocialAuthenticationProvider extends AbstractAuthenticationProvider<SocialAuthenticationToken> {

    private final AdminUserDetailsService adminUserDetailsService;

    public SocialAuthenticationProvider(AdminUserDetailsService adminUserDetailsService,
                                        OAuth2AuthorizationService authorizationService,
                                        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        super(authorizationService, tokenGenerator);
        this.adminUserDetailsService = adminUserDetailsService;
    }

    @Override
    public SocialAuthenticationToken authenticationToken(Authentication authentication) {
        SocialAuthenticationToken authenticationToken = (SocialAuthenticationToken) authentication;

        try {
            AuthParams authParams = (AuthParams) authenticationToken.getPrincipal();
            UserDetails userDetails = adminUserDetailsService.loadAuthUser(authParams);

            // 检查账号状态
            super.accountStatusChecker.check(userDetails);

            AuthorizationGrantType grantType = authenticationToken.getGrantType();
            return new SocialAuthenticationToken(grantType, userDetails, userDetails.getAuthorities());
        } catch (AbstractException e) {
            log.debug("Authentication failed: no credentials provided ex={}", e.getMessage());
            throw new UsernameNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.debug("Authentication failed: no credentials provided ex={}", e.getMessage(), e);
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.noopBindAccount",
                    "Noop Bind Account"));
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SocialAuthenticationProvider.class.isAssignableFrom(authentication);
    }
}
