package com.thinker.cloud.auth.core.userdetails;

import com.thinker.cloud.security.enums.LoginTypeEnum;
import com.thinker.cloud.security.model.AuthParams;
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
     * 根据登录账号名获取用户详情
     *
     * @param username the username identifying the user whose data is required.
     * @return UserDetails
     */
    @Override
    default UserDetails loadUserByUsername(String username) {
        return this.loadUserByAuthParams(new AuthParams(LoginTypeEnum.PASSWORD, username, null));
    }

    /**
     * 根据授权参数获取认证主体信息
     *
     * @param authParams authParams
     * @return UserDetails
     */
    UserDetails loadUserByAuthParams(AuthParams authParams);

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
