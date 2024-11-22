package com.thinker.cloud.auth.core.support.sms;

import com.thinker.cloud.auth.core.support.base.AbstractAuthenticationProvider;
import com.thinker.cloud.auth.core.userdetails.BaseUserDetailsService;
import com.thinker.cloud.security.model.AuthParams;
import com.thinker.cloud.security.token.SmsAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * 短信登录认证转换器
 *
 * @author admin
 **/
@Slf4j
public class SmsAuthenticationProvider extends AbstractAuthenticationProvider<SmsAuthenticationToken> {

    private final BaseUserDetailsService userDetailsService;

    public SmsAuthenticationProvider(BaseUserDetailsService userDetailsService,
                                     OAuth2AuthorizationService authorizationService,
                                     OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        super(authorizationService, tokenGenerator);
        this.userDetailsService = userDetailsService;
    }

    @Override
    public SmsAuthenticationToken authenticationPrincipal(Authentication authentication) {
        AuthParams authParams = (AuthParams) authentication.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByAuthParams(authParams);

        // 检查账号状态
        super.accountStatusChecker.check(userDetails);

        // 构建验证身份主体
        return new SmsAuthenticationToken(userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
