package com.thinker.cloud.upms.uac.handler;

import com.thinker.cloud.core.exception.FailException;
import com.thinker.cloud.upms.api.uac.enums.AuthTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 登录认证处理器工厂类
 *
 * @author admin
 **/
@Slf4j
@Component
public class AuthLoginHandlerFactory {

    private final Map<AuthTypeEnum, AuthLoginHandler> authLoginHandlerMap;

    public AuthLoginHandlerFactory(List<AbstractAuthLoginHandler> authLoginHandlerList) {
        this.authLoginHandlerMap = authLoginHandlerList.stream()
                .collect(Collectors.toMap(AbstractAuthLoginHandler::getAuthType, value -> value));
    }

    /**
     * 根据认证类型获取登录处理器
     *
     * @param authType 认证类型
     * @return AuthLoginHandler
     */
    public AuthLoginHandler get(String authType) {
        AuthTypeEnum authTypeEnum = AuthTypeEnum.resolver(authType);
        AuthLoginHandler loginHandler = authLoginHandlerMap.get(authTypeEnum);
        return Optional.ofNullable(loginHandler).orElseThrow(() -> FailException.of("暂不支持此认证方式"));
    }
}
