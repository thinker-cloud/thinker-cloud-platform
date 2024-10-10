package com.thinker.cloud.auth.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.thinker.cloud.core.model.entity.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * oauth2客户端社交账号配置
 *
 * @author admin
 * @since 2024-10-10 10:58:49
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "oauth_client_social")
public class OauthClientSocial extends SuperEntity {

    @Serial
    private static final long serialVersionUID = 1L;

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
}
