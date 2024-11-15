package com.thinker.cloud.upms.api.sys.client;

import com.thinker.cloud.security.annotation.Inner;
import com.thinker.cloud.upms.api.uac.model.AuthParams;
import com.thinker.cloud.upms.api.uac.model.AuthUserDetail;

/**
 * 会员对外API相关接口
 *
 * @author admin
 **/
public interface IMemberClient {

    /**
     * 根据认证参数获取会员用户信息
     *
     * @param params params
     * @return AuthUser
     */
    @Inner
    AuthUserDetail getMemberUser(AuthParams params);

}
