package com.thinker.cloud.upms.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thinker.cloud.core.model.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息
 *
 * @author admin
 * @since 2024-09-20 17:25:48
 */
@Data
@Accessors(chain = true)
@TableName(value = "sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends TenantEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 登录账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String salt;

    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户类型 -1.超级管理员 1.租户管理员 2.普通用户
     */
    private Integer type;

    /**
     * 状态 -1.锁定 1.可用 2.过期
     */
    private Integer status;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别 -1.未知 0.男 1.女
     */
    private Integer sex;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 微信openId
     */
    private String wxOpenId;

    /**
     * 小程序openId
     */
    private String miniOpenId;

    /**
     * 组织部门id
     */
    private Long organizationId;
}
