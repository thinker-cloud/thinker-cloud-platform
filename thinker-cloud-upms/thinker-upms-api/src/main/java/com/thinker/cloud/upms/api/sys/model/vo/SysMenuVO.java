package com.thinker.cloud.upms.api.sys.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单管理
 *
 * @author admin
 * @since 2024-09-27 17:43:02
 */
@Data
@Accessors(chain = true)
public class SysMenuVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

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

    /**
     * 创建人id
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人id
     */
    private Long updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
