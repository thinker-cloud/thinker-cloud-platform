package com.thinker.cloud.upms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.thinker.cloud.core.model.entity.SuperEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

/**
 * 角色管理
 *
 * @author admin
 * @since 2024-09-23 11:39:09
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_role")
public class SysRole extends SuperEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 启用状态 0.禁用, 1.启用
     */
    private Boolean enabled;

    /**
     * 数据权限类型 1.全部 2.本机及子级 3.本级 4.当前用户 10.自定义
     */
    private Integer dataScopeType;

    /**
     * 备注描述
     */
    private String description;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 租户id
     */
    private Long tenantId;
}
