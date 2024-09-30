package com.thinker.cloud.upms.api.uac.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 认证参数
 *
 * @author admin
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthParams implements Serializable {

    @Serial
    private static final long serialVersionUID = -6324518242471481854L;

    /**
     * 认证类型
     */
    @NotBlank(message = "认证类型不能为空")
    private String authType;

    /**
     * 认证主体
     */
    @NotBlank(message = "认证主体不能为空")
    private String subject;

    /**
     * 认证凭证
     */
    @NotBlank(message = "认证凭证不能为空")
    private String credential;

}
