package com.thinker.cloud.upms.api.sys.client;

import com.thinker.cloud.security.model.AuthParams;
import com.thinker.cloud.upms.api.sys.model.vo.SysUserVO;
import com.thinker.cloud.upms.api.uac.model.AuthUserDetail;

/**
 * 用户对外API相关接口
 *
 * @author admin
 **/
public interface IUserClient {

    /**
     * 根据认证参数获取后台用户信息
     *
     * @param params params
     * @return AuthUser
     */
    AuthUserDetail getAuthUser(AuthParams params);

    /**
     * 根据用户id获取用户信息
     *
     * @param id id
     * @return SysUserVO
     */
    SysUserVO getUserById(Long id);
}
