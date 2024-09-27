package com.thinker.cloud.upms.api.sys.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 业务字典
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Data
@Accessors(chain = true)
public class SysDictBizDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典名称
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注描述
     */
    private String description;

    /**
     * 扩展信息
     */
    private String extendsInfo;

    /**
     * 租户id
     */
    private Long tenantId;
}
