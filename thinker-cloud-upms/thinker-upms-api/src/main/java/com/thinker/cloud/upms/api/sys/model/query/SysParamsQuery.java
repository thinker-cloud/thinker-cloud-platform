package com.thinker.cloud.upms.api.sys.model.query;

import com.thinker.cloud.core.model.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 公共参数
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysParamsQuery extends PageQuery {

    /**
     * 主键
     */
    private Long id;

    /**
     * 主键列表
     */
    private Collection<Long> ids;

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
