package com.thinker.cloud.auth.core.handler;

import com.thinker.cloud.security.handler.AuthenticationFailureHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 登录失败事件处理
 *
 * @author admin
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationFailureEvenHandler implements AuthenticationFailureHandler {

    private final ApplicationEventPublisher publisher;

    @Async
    @Override
    @SneakyThrows
    public void handle(AuthenticationException authenticationException, Authentication authentication,
                       HttpServletRequest request, HttpServletResponse response) {
        String username = authentication.getName();
        log.info("用户：{} 登录失败，异常：{}", username, authenticationException.getLocalizedMessage());
    }
}
