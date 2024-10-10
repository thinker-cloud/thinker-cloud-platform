package com.thinker.cloud.auth.api.model.query;

import com.thinker.cloud.core.model.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * oauth2客户端社交账号配置
 *
 * @author admin
 * @since 2024-10-10 10:58:49
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class OauthClientSocialQuery extends PageQuery {

    /**
     * 主键
     */
    private Long id;

    /**
     * 主键列表
     */
    private Collection<Long> ids;

    /**
     * 应用类型
     */
    private String type;

    /**
     * 应用id
     */
    private String appId;

    /**
     * 应用密匙
     */
    private String appSecret;

    /**
     * 回调地址
     */
    private String redirectUrl;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 认证客户端id
     */
    private Long oauthClientId;

    /**
     * 备注描述
     */
    private String description;

    /**
     * 租户id
     */
    private Long tenantId;

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
