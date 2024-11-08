package com.thinker.cloud.auth.api.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 前端展示令牌管理
 *
 * @author admin
 */
@Data
@Accessors(chain = true)
public class AccessTokenVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1017226354833764543L;

    /**
     * 主体id
     */
    private String id;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 主体名称
     */
    private String username;

    /**
     * access_token
     */
    private String accessToken;

    /**
     * 生成时间
     */
    private String issuedAt;

    /**
     * 过期时间
     */
    private String expiresAt;
}
