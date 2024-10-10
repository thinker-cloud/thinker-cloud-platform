package com.thinker.cloud.auth.api.model.query;

import com.thinker.cloud.core.model.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * oauth2客户端配置
 *
 * @author admin
 * @since 2024-10-10 10:58:49
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class OauthClientQuery extends PageQuery {

    /**
     * 主键
     */
    private Long id;

    /**
     * 主键列表
     */
    private Collection<Long> ids;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端key
     */
    private String clientKey;

    /**
     * 客户端秘钥
     */
    private String clientSecret;

    /**
     * 授权范围
     */
    private String scope;

    /**
     * 授权类型
     */
    private String grantTypes;

    /**
     * 是否启用 0.禁用 1.启用
     */
    private Boolean enabled;

    /**
     * 回调跳转URL
     */
    private String redirectUri;

    /**
     * 访问令牌有效期（秒）
     */
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效期（秒）
     */
    private Integer refreshTokenValidity;

    /**
     * 前端密码加密
     */
    private Boolean isEncoder;

    /**
     * 验证码开关
     */
    private Boolean isCaptcha;

    /**
     * IP白名单
     */
    private String ipWhiteList;

    /**
     * 附加信息
     */
    private String additionalInfo;

    /**
     * 自动授权
     */
    private String autoApprove;

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
