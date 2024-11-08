package com.thinker.cloud.auth.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.thinker.cloud.core.model.entity.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * oauth2客户端配置
 *
 * @author admin
 * @since 2024-10-10 10:58:49
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "oauth_client")
public class OauthClient extends SuperEntity {

    @Serial
    private static final long serialVersionUID = 1L;

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
    private String[] grantTypes;

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
}
