package com.thinker.cloud.upms.api.sys.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * 授权信息
 *
 * @author admin
 */
@Data
@Accessors(chain = true)
public class AuthorizeVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5590665921554011618L;

    /**
     * 数据权限类型
     */
    private Integer dataScopeType;

    /**
     * 数据权限列表
     */
    private Collection<String> dataScopeIds;

    /**
     * 菜单权限列表
     */
    private Collection<String> menus;

    /**
     * 菜单接口权限列表
     */
    private Collection<String> permissions;

    /**
     * 角色编码列表
     */
    private Collection<String> roles;
}
