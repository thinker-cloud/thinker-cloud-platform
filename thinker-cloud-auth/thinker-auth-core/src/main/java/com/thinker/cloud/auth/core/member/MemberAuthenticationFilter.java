package com.thinker.cloud.auth.core.member;

import com.thinker.cloud.auth.core.support.password.PasswordAuthenticationConverter;
import com.thinker.cloud.auth.core.support.sms.SmsAuthenticationConverter;
import com.thinker.cloud.common.enums.AuthTypeEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.web.OAuth2ClientAuthenticationFilter;
import org.springframework.security.oauth2.server.authorization.web.authentication.DelegatingAuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;

/**
 * 会员授权验证Filter
 *
 * @author admin
 **/
@Slf4j
@Setter
public class MemberAuthenticationFilter extends OncePerRequestFilter {

    private final RequestMatcher authorizationEndpointMatcher;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationConverter authenticationConverter;

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    public MemberAuthenticationFilter(HttpSecurity httpSecurity) {
        this(httpSecurity, AuthTypeEnum.MEMBER.getPath());
    }

    public MemberAuthenticationFilter(HttpSecurity httpSecurity, String authorizationEndpointUri) {
        Assert.notNull(httpSecurity, "httpSecurity cannot be null");
        Assert.hasText(authorizationEndpointUri, "authorizationEndpointUri cannot be empty");
        AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);
        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        this.authenticationManager = authenticationManager;
        this.authorizationEndpointMatcher = new AntPathRequestMatcher(authorizationEndpointUri, HttpMethod.POST.name());

        OAuth2ClientAuthenticationFilter filter = httpSecurity.getSharedObject(OAuth2ClientAuthenticationFilter.class);

        // @formatter:off
        this.authenticationConverter = new DelegatingAuthenticationConverter(Arrays.asList(
                        new PasswordAuthenticationConverter(),
                        new SmsAuthenticationConverter()));
        // @formatter:on
    }

    @Override
    @SneakyThrows
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {
        if (!authorizationEndpointMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = authenticationConverter.convert(request);
            if (authentication instanceof AbstractAuthenticationToken) {
                ((AbstractAuthenticationToken) authentication)
                        .setDetails(authenticationDetailsSource.buildDetails(request));
            }

            Authentication authenticationResult = authenticationManager.authenticate(authentication);
            if (!authenticationResult.isAuthenticated()) {
                filterChain.doFilter(request, response);
                return;
            }

            // 认证成功
            authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();

            // 认证失败
            authenticationFailureHandler.onAuthenticationFailure(request, response, e);
        }
    }
}
