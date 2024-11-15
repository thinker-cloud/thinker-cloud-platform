package com.thinker.cloud.auth.core.handler;

import com.thinker.cloud.security.component.Oauth2AuthExceptionEntryPoint;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * 登录失败事件处理
 *
 * @author admin
 */
@Slf4j
@Component
@AllArgsConstructor
public class Oauth2AuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final Oauth2AuthExceptionEntryPoint authExceptionEntryPoint;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        log.error("登录失败，异常：{}", e.getLocalizedMessage());

        // 认证失败异常统一处理
        authExceptionEntryPoint.commence(request, response, e);
    }
}
