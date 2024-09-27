package com.thinker.cloud.upms.api.sys.model.query;

import com.thinker.cloud.core.model.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 租户管理
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysTenantQuery extends PageQuery {

    /**
     * 租户id
     */
    private Long id;

    /**
     * 租户id列表
     */
    private Collection<Long> ids;

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
     * 筛选起始:租户生效时间
     */
    private LocalDateTime startStartTime;

    /**
     * 筛选结束:租户生效时间
     */
    private LocalDateTime endStartTime;

    /**
     * 租户到期时间
     */
    private LocalDateTime endTime;

    /**
     * 筛选起始:租户到期时间
     */
    private LocalDateTime startEndTime;

    /**
     * 筛选结束:租户到期时间
     */
    private LocalDateTime endEndTime;

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

    /**
     * 是否包含删除数据
     */
    private Boolean isIncludeDelete;

    /**
     * 创建人id
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 筛选起始:创建时间
     */
    private LocalDateTime startCreateTime;

    /**
     * 筛选结束:创建时间
     */
    private LocalDateTime endCreateTime;

    /**
     * 修改人id
     */
    private Long updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 筛选起始:修改时间
     */
    private LocalDateTime startUpdateTime;

    /**
     * 筛选结束:修改时间
     */
    private LocalDateTime endUpdateTime;
}
