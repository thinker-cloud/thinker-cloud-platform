package com.thinker.cloud.upms.api.sys.model.vo;

import com.thinker.cloud.core.utils.tree.TreeNode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 组织架构
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Data
@Accessors(chain = true)
public class SysOrganizationVO implements TreeNode<SysOrganizationVO>, Serializable {

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
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 启用状态 0.禁用 1.启用
     */
    private Boolean enabled;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 描述
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

    /**
     * 子级列表
     */
    private List<SysOrganizationVO> subList;
}
