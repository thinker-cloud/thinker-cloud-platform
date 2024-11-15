package com.thinker.cloud.auth.core.support.password;

import com.thinker.cloud.auth.core.support.base.BaseAuthenticationProvider;
import com.thinker.cloud.auth.core.userdetails.BaseUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * 密码登录认证转换器
 *
 * @author admin
 **/
@Slf4j
public class PasswordAuthenticationProvider extends BaseAuthenticationProvider<PasswordAuthenticationToken> {

    private final PasswordEncoder passwordEncoder;
    private final BaseUserDetailsService userDetailsService;

    public PasswordAuthenticationProvider(PasswordEncoder passwordEncoder,
                                          BaseUserDetailsService userDetailsService,
                                          OAuth2AuthorizationService authorizationService,
                                          OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        super(authorizationService, tokenGenerator);
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public PasswordAuthenticationToken authenticationPrincipal(Authentication authentication) {
        PasswordAuthenticationToken authenticationToken = (PasswordAuthenticationToken) authentication;
        String username = (String) authentication.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 检查账号状态
        super.accountStatusChecker.check(userDetails);

        // 校验登录密码
        String password = (String) authenticationToken.getCredentials();
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }

        // 构建验证身份主体
        return new PasswordAuthenticationToken(authenticationToken.getGrantType(), userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}