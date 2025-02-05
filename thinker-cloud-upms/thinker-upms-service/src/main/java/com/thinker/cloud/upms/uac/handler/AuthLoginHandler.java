package com.thinker.cloud.upms.uac.handler;


import com.thinker.cloud.security.model.AuthParams;
import com.thinker.cloud.upms.api.uac.model.AuthUserDetail;

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
     * @return AuthUserDetail
     */
    AuthUserDetail getAuthUser(AuthParams authParam);

    /**
     * 获取会员认证用户
     *
     * @param authParam 认证参数
     * @return AuthUserDetail
     */
    AuthUserDetail getMemberUser(AuthParams authParam);
}
