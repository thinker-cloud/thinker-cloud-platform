package com.thinker.cloud.upms.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.thinker.cloud.core.model.entity.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 公共参数
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_params")
public class SysParams extends SuperEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参数名
     */
    private String paramName;

    /**
     * 参数键
     */
    private String paramKey;

    /**
     * 参数值
     */
    private String paramValue;

    /**
     * 启用状态 0.禁用 1.启用
     */
    private Boolean enabled;

    /**
     * 备注描述
     */
    private String description;
}
