package com.thinker.cloud.auth.api.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * oauth2客户端社交账号配置
 *
 * @author admin
 * @since 2024-10-10 10:58:49
 */
@Data
@Accessors(chain = true)
public class OauthClientSocialVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

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
