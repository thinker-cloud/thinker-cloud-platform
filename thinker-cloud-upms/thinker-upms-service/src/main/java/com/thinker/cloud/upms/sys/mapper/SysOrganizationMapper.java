package com.thinker.cloud.upms.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.db.dynamic.datasource.annotation.Slave;
import com.thinker.cloud.upms.api.sys.model.query.SysOrganizationQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysOrganizationVO;
import com.thinker.cloud.upms.sys.model.entity.SysOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * 组织架构
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Mapper
public interface SysOrganizationMapper extends BaseMapper<SysOrganization> {

    /**
     * 根据query分页查询
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 分页列表
     */
    @Slave
    List<SysOrganizationVO> page(@Param("page") IPage<SysOrganizationVO> page, @Param("query") SysOrganizationQuery query);

    /**
     * 根据query查询列表
     *
     * @param query 查询条件
     * @return 列表
     */
    @Slave
    List<SysOrganizationVO> list(@Param("query") SysOrganizationQuery query);

    /**
     * 根据query查询ids
     *
     * @param query 查询条件
     * @return List<Long>
     */
    @Slave
    List<Long> idsByQuery(@Param("query") SysOrganizationQuery query);

    /**
     * 根据查询条件统计数量
     *
     * @param query 查询条件
     * @return Integer
     */
    @Slave
    Integer countByQuery(@Param("query") SysOrganizationQuery query);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return SysOrganization
     */
    @Slave
    @Override
    SysOrganization selectById(@Param("id") Serializable id);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return SysOrganizationVO
     */
    @Slave
    SysOrganizationVO findDetail(@Param("id") Long id);

    /**
     * 根据query获取本级及子级ids
     *
     * @param query query
     * @return List<Long>
     */
    @Slave
    List<Long> listAndSubIds(SysOrganizationQuery query);

    /**
     * 根据query获取本级及子级
     *
     * @param query query
     * @return List<SysOrganizationVO>
     */
    @Slave
    List<SysOrganizationVO> listAndSubList(SysOrganizationQuery query);
}
