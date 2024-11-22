package com.thinker.cloud.auth.core.listener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 认证失败事件监听器
 *
 * @author admin
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationFailureEventListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(@NonNull AbstractAuthenticationFailureEvent event) {
        AuthenticationException e = event.getException();
        Authentication authentication = event.getAuthentication();
        log.error("用户：{} 认证失败事件监听，异常：{}", authentication.getName(), e.getLocalizedMessage());
    }
}
