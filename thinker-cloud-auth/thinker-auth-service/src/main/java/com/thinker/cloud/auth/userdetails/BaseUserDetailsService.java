package com.thinker.cloud.auth.userdetails;

import com.thinker.cloud.upms.api.uac.model.AuthParams;
import org.springframework.core.Ordered;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 用户统一授权接口
 *
 * @author admin
 **/
public interface BaseUserDetailsService extends UserDetailsService, Ordered {

    /**
     * 获取认证主体用户信息
     *
     * @param authParams authParams
     * @return UserDetails
     */
    UserDetails loadAuthUser(AuthParams authParams);

    /**
     * 排序值 默认取最大的
     *
     * @return 排序值
     */
    @Override
    default int getOrder() {
        return 0;
    }
}
