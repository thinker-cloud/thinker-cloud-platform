package com.thinker.cloud.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.db.dynamic.datasource.annotation.Slave;
import com.thinker.cloud.upms.model.vo.SysRoleVO;
import com.thinker.cloud.upms.model.entity.SysRole;
import com.thinker.cloud.upms.model.query.SysRoleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * 角色管理
 *
 * @author admin
 * @since 2024-09-23 11:39:09
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据query分页查询
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 分页列表
     */
    @Slave
    List<SysRoleVO> page(@Param("page") IPage<SysRoleVO> page, @Param("query") SysRoleQuery query);

    /**
     * 根据query查询列表
     *
     * @param query 查询条件
     * @return 列表
     */
    @Slave
    List<SysRoleVO> list(@Param("query") SysRoleQuery query);

    /**
     * 根据query查询ids
     *
     * @param query 查询条件
     * @return List<Long>
     */
    @Slave
    List<Long> idsByQuery(@Param("query") SysRoleQuery query);

    /**
     * 根据查询条件统计数量
     *
     * @param query 查询条件
     * @return Integer
     */
    @Slave
    Integer countByQuery(@Param("query") SysRoleQuery query);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return SysRole
     */
    @Slave
    @Override
    SysRole selectById(@Param("id") Serializable id);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return SysRoleVO
     */
    @Slave
    SysRoleVO findDetail(@Param("id") Long id);
}
