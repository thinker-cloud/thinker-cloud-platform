package com.thinker.cloud.upms.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.thinker.cloud.core.model.entity.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 租户管理
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_tenant")
public class SysTenant extends SuperEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 租户名称
     */
    private String name;

    /**
     * 租户编码
     */
    private String code;

    /**
     * 租户地址
     */
    private String address;

    /**
     * 租户生效时间
     */
    private LocalDateTime startTime;

    /**
     * 租户到期时间
     */
    private LocalDateTime endTime;

    /**
     * 最大用户数
     */
    private Integer maxUser;

    /**
     * 启用状态 0.禁用, 1.启用
     */
    private Boolean enabled;

    /**
     * 系统名称
     */
    private String sysName;

    /**
     * 系统简称
     */
    private String sysAbbr;

    /**
     * 管理员用户id
     */
    private Long adminId;

    /**
     * 管理员初始密码
     */
    private String password;

    /**
     * 首页页面
     */
    private String homePage;

    /**
     * 租户简介
     */
    private String description;

    /**
     * 扩展信息
     */
    private String extendsInfo;
}
