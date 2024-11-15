package com.thinker.cloud.auth.core.listener;

import com.thinker.cloud.security.userdetail.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * 认证成功事件监听器
 *
 * @author admin
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = (Authentication) event.getSource();
        if (authentication.getPrincipal() instanceof AuthUser) {
            log.info("用户：{} 登录成功事件监听", authentication.getName());
        }
    }
}
