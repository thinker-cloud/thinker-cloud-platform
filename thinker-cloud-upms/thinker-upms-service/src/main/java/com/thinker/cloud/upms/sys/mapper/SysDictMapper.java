package com.thinker.cloud.upms.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.db.dynamic.datasource.annotation.Slave;
import com.thinker.cloud.upms.api.sys.model.query.SysDictQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysDictVO;
import com.thinker.cloud.upms.sys.model.entity.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * 公共字典
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 根据query分页查询
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 分页列表
     */
    @Slave
    List<SysDictVO> page(@Param("page") IPage<SysDictVO> page, @Param("query") SysDictQuery query);

    /**
     * 根据query查询列表
     *
     * @param query 查询条件
     * @return 列表
     */
    @Slave
    List<SysDictVO> list(@Param("query") SysDictQuery query);

    /**
     * 根据query查询ids
     *
     * @param query 查询条件
     * @return List<Long>
     */
    @Slave
    List<Long> idsByQuery(@Param("query") SysDictQuery query);

    /**
     * 根据查询条件统计数量
     *
     * @param query 查询条件
     * @return Integer
     */
    @Slave
    Integer countByQuery(@Param("query") SysDictQuery query);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return SysDict
     */
    @Slave
    @Override
    SysDict selectById(@Param("id") Serializable id);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return SysDictVO
     */
    @Slave
    SysDictVO findDetail(@Param("id") Long id);
}
