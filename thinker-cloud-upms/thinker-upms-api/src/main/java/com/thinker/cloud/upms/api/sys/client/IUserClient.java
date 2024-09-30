package com.thinker.cloud.upms.api.sys.client;

import com.thinker.cloud.upms.api.uac.model.AuthParams;
import com.thinker.cloud.upms.api.uac.model.AuthUser;

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
    AuthUser getAuthUser(@Valid AuthParams params);

}
