package com.thinker.cloud.upms.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.thinker.cloud.core.model.entity.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 业务字典
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_dict_biz")
public class SysDictBiz extends SuperEntity {

    @Serial
    private static final long serialVersionUID = 1L;

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
