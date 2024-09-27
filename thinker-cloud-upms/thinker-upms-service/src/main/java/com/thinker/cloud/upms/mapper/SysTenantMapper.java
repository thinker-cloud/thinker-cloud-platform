package com.thinker.cloud.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.db.dynamic.datasource.annotation.Slave;
import com.thinker.cloud.upms.api.sys.model.query.SysTenantQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysTenantVO;
import com.thinker.cloud.upms.model.entity.SysTenant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * 租户管理
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Mapper
public interface SysTenantMapper extends BaseMapper<SysTenant> {

    /**
     * 根据query分页查询
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 分页列表
     */
    @Slave
    List<SysTenantVO> page(@Param("page") IPage<SysTenantVO> page, @Param("query") SysTenantQuery query);

    /**
     * 根据query查询列表
     *
     * @param query 查询条件
     * @return 列表
     */
    @Slave
    List<SysTenantVO> list(@Param("query") SysTenantQuery query);

    /**
     * 根据query查询ids
     *
     * @param query 查询条件
     * @return List<Long>
     */
    @Slave
    List<Long> idsByQuery(@Param("query") SysTenantQuery query);

    /**
     * 根据查询条件统计数量
     *
     * @param query 查询条件
     * @return Integer
     */
    @Slave
    Integer countByQuery(@Param("query") SysTenantQuery query);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return SysTenant
     */
    @Slave
    @Override
    SysTenant selectById(@Param("id") Serializable id);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return SysTenantVO
     */
    @Slave
    SysTenantVO findDetail(@Param("id") Long id);
}
