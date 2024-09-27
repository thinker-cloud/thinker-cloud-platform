package com.thinker.cloud.upms.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.thinker.cloud.core.model.entity.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 菜单管理
 *
 * @author admin
 * @since 2024-09-27 17:43:02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_menu")
public class SysMenu extends SuperEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 菜单平台 1.PC 2.APP 3.小程序
     */
    private Integer platform;

    /**
     * 状态 0.禁用 1.启用
     */
    private Boolean enabled;

    /**
     * 类型 0.菜单 1.按钮
     */
    private Boolean type;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 组件地址
     */
    private String route;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否隐藏 0.否 1.是
     */
    private Boolean hidden;

    /**
     * 是否缓存 0.否 1.是
     */
    private Boolean keepAlive;

    /**
     * 备注描述
     */
    private String description;
}
