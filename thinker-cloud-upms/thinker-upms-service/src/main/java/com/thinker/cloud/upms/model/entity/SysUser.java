package com.thinker.cloud.upms.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户信息
 *
 * @author admin
 * @since 2024-09-20 17:25:48
 */
@Data
@Accessors(chain = true)
@TableName(value = "sys_user")
public class SysUser implements Serializable {

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

    /**
     * 版本号
     */
    @Version
    private Integer version;

    /**
     * 删除标识 0.未删除 1.已删除
     */
    @TableLogic
    private Boolean deleted;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人id
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
