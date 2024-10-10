package com.thinker.cloud.upms.api.sys.client;

import com.thinker.cloud.security.annotation.Inner;
import com.thinker.cloud.upms.api.uac.model.AuthParams;
import com.thinker.cloud.upms.api.uac.model.AuthUserDetail;

import javax.validation.Valid;

/**
 * 用户对外API相关接口
 *
 * @author admin
 **/
public interface IUserClient {

    /**
     * 根据认证参数获取用户信息
     *
     * @param params params
     * @return AuthUser
     */
    @Inner
    AuthUserDetail getAuthUser(@Valid AuthParams params);

}
