package com.thinker.cloud.upms.uac.handler;


import com.thinker.cloud.core.exception.FailException;
import com.thinker.cloud.upms.api.sys.model.vo.AuthorizeVO;
import com.thinker.cloud.upms.api.uac.enums.LoginTypeEnum;
import com.thinker.cloud.upms.api.uac.model.AuthUserDetail;
import com.thinker.cloud.upms.sys.converter.SysUserConverter;
import com.thinker.cloud.upms.sys.model.entity.SysUser;
import com.thinker.cloud.upms.sys.service.ISysUserService;
import jakarta.annotation.Resource;

import java.util.Optional;

/**
 * 登录处理器抽象类
 *
 * @author admin
 */
public abstract class AbstractAuthLoginHandler implements AuthLoginHandler {

    @Resource
    protected ISysUserService userService;

    /**
     * 获取用户授权信息
     */
    protected AuthUserDetail getAuthUser(SysUser sysUser) {
        Optional.ofNullable(sysUser).orElseThrow(() -> FailException.of("不存在该用户"));
        AuthUserDetail authUser = SysUserConverter.INSTANTS.toAuth(sysUser);

        // 设置角色、菜单、数据权限列表
        AuthorizeVO authorize = userService.getAuthorize(sysUser);
        Optional.ofNullable(authorize.getRoles()).ifPresent(authUser::setRoles);
        Optional.ofNullable(authorize.getMenus()).ifPresent(authUser::setMenus);
        Optional.ofNullable(authorize.getPermissions()).ifPresent(authUser::setPermissions);
        Optional.ofNullable(authorize.getDataScopeIds()).ifPresent(authUser::setDataScopeIds);
        Optional.ofNullable(authorize.getDataScopeType()).ifPresent(authUser::setDataScopeType);
        return authUser;
    }

    /**
     * 获取认证类型
     *
     * @return AuthTypeEnum
     */
    protected abstract LoginTypeEnum getAuthType();

}
