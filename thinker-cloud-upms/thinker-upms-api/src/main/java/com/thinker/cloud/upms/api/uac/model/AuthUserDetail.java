package com.thinker.cloud.upms.api.uac.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 授权用户信息
 *
 * @author admin
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = -4816804440827648554L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户类型 -1.超级管理员 1.租户管理员 2.普通用户
     */
    private Integer type;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 组织机构id
     */
    private Long organizationId;

    /**
     * 数据权限类型 1.全部 2.本机及子级 3.本级 4.当前用户 10.自定义
     */
    private Integer dataScopeType;

    /**
     * 角色编码列表
     */
    private Collection<String> roles = new ArrayList<>();

    /**
     * 菜单权限列表
     */
    private Collection<String> menus = new ArrayList<>();

    /**
     * 菜单接口权限列表
     */
    private Collection<String> permissions = new ArrayList<>();

    /**
     * 数据权限列表
     */
    private Collection<String> dataScopeIds = new ArrayList<>();
}
