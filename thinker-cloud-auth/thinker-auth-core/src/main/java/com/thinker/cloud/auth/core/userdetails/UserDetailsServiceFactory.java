package com.thinker.cloud.auth.core.userdetails;

import com.thinker.cloud.common.constants.CommonConstants;
import com.thinker.cloud.common.enums.AuthTypeEnum;
import com.thinker.cloud.security.token.AbstractAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户统一授权工厂
 *
 * @author xfy
 * @since 2024-11-29 17:54
 **/
@Component
public class UserDetailsServiceFactory {

    private final Map<AuthTypeEnum, BaseUserDetailsService> userDetailsServiceMap;

    public UserDetailsServiceFactory(List<BaseUserDetailsService> userDetailsServices) {
        this.userDetailsServiceMap = userDetailsServices.stream()
                .collect(Collectors.toMap(BaseUserDetailsService::getAuthType, value -> value));
    }

    /**
     * 根据授权认证类型获取 UserDetailsService
     *
     * @param authType authType
     * @return BaseUserDetailsService
     */
    public BaseUserDetailsService get(String authType) {
        AuthTypeEnum resolver = AuthTypeEnum.resolver(authType);
        return userDetailsServiceMap.get(resolver);
    }

    /**
     * 根据授权认证类型获取 UserDetailsService
     *
     * @param authentication authentication
     * @return BaseUserDetailsService
     */
    public BaseUserDetailsService get(AbstractAuthenticationToken authentication) {
        Object clientAuth = authentication.getParameters(CommonConstants.AUTH_TYPE_HEADER);
        AuthTypeEnum resolver = AuthTypeEnum.resolver((String) clientAuth);
        return userDetailsServiceMap.get(resolver);
    }
}
