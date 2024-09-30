package com.thinker.cloud.upms.uac.handler;


import com.thinker.cloud.upms.api.uac.model.AuthParams;
import com.thinker.cloud.upms.api.uac.model.AuthUser;

/**
 * 登录处理器
 *
 * @author admin
 */
public interface AuthLoginHandler {

    /**
     * 获取系统认证用户
     *
     * @param authParam 认证参数
     * @return AuthUser
     */
    AuthUser getAuthUser(AuthParams authParam);
}
