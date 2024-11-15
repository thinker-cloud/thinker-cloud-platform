package com.thinker.cloud.upms.api.sys.model.query;

import com.thinker.cloud.core.model.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 用户信息
 *
 * @author admin
 * @since 2024-09-20 17:25:48
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysUserQuery extends PageQuery {

    /**
     * 主键
     */
    private Long id;

    /**
     * 主键列表
     */
    private Collection<Long> ids;

    /**
     * 姓名
     */
    private String name;

    /**
     * 登录账号
     */
    private String username;

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
     * 是否包含删除数据
     */
    private Boolean isIncludeDelete;

    /**
     * 租户id
     */
    private Long tenantId;

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
