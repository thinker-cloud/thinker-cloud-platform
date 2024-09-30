package com.thinker.cloud.upms.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.db.dynamic.datasource.annotation.Slave;
import com.thinker.cloud.upms.api.sys.model.query.SysMenuQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysMenuVO;
import com.thinker.cloud.upms.sys.model.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单管理
 *
 * @author admin
 * @since 2024-09-27 17:43:02
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据query分页查询
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 分页列表
     */
    @Slave
    List<SysMenuVO> page(@Param("page") IPage<SysMenuVO> page, @Param("query") SysMenuQuery query);

    /**
     * 根据query查询列表
     *
     * @param query 查询条件
     * @return 列表
     */
    @Slave
    List<SysMenuVO> list(@Param("query") SysMenuQuery query);

    /**
     * 根据query查询ids
     *
     * @param query 查询条件
     * @return List<Long>
     */
    @Slave
    List<Long> idsByQuery(@Param("query") SysMenuQuery query);

    /**
     * 根据查询条件统计数量
     *
     * @param query 查询条件
     * @return Integer
     */
    @Slave
    Integer countByQuery(@Param("query") SysMenuQuery query);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return SysMenu
     */
    @Slave
    @Override
    SysMenu selectById(@Param("id") Serializable id);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return SysMenuVO
     */
    @Slave
    SysMenuVO findDetail(@Param("id") Long id);
}
