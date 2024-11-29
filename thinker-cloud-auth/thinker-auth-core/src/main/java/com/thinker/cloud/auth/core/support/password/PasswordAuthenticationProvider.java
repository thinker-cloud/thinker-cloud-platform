package com.thinker.cloud.auth.core.support.password;

import com.thinker.cloud.auth.core.support.AbstractAuthenticationProvider;
import com.thinker.cloud.auth.core.userdetails.BaseUserDetailsService;
import com.thinker.cloud.security.token.PasswordAuthenticationToken;
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
public class PasswordAuthenticationProvider extends AbstractAuthenticationProvider<PasswordAuthenticationToken> {

    private final PasswordEncoder passwordEncoder;

    public PasswordAuthenticationProvider(PasswordEncoder passwordEncoder,
                                          OAuth2AuthorizationService authorizationService,
                                          OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        super(authorizationService, tokenGenerator);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PasswordAuthenticationToken authenticationPrincipal(Authentication authentication) {
        PasswordAuthenticationToken authenticationToken = (PasswordAuthenticationToken) authentication;
        BaseUserDetailsService userDetailsService = super.getUserDetailsService(authenticationToken);

        // 获取登录用户信息
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
        return new PasswordAuthenticationToken(userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
