package com.thinker.cloud.upms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thinker.cloud.upms.model.vo.SysRoleVO;
import com.thinker.cloud.upms.model.entity.SysRole;
import com.thinker.cloud.upms.model.dto.SysRoleDTO;
import com.thinker.cloud.upms.model.query.SysRoleQuery;

import java.util.List;

/**
 * 角色管理 服务类
 *
 * @author admin
 * @since 2024-09-23 11:39:09
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 根据query分页查询
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 数据列表
     */
    List<SysRoleVO> page(IPage<SysRoleVO> page, SysRoleQuery query);

    /**
     * 根据query查询
     *
     * @param query 查询条件
     * @return 数据列表
     */
    List<SysRoleVO> list(SysRoleQuery query);

    /**
     * 根据query查询ids
     *
     * @param query 查询条件
     * @return List<Long>
     */
    List<Long> idsByQuery(SysRoleQuery query);

    /**
     * 根据query统计数量
     *
     * @param query 查询条件
     * @return Integer
     */
    Integer countByQuery(SysRoleQuery query);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return SysRoleVO
     */
    SysRoleVO findDetail(Long id);

    /**
     * 保存,逻辑处理
     *
     * @param dto 数据实体
     * @return 是否保存
     */
    Boolean saveData(SysRoleDTO dto);

    /**
     * 根据id修改
     *
     * @param dto 数据实体
     * @return 是否保存
     */
    Boolean modifyById(SysRoleDTO dto);

    /**
     * 根据id删除,逻辑处理
     *
     * @param id 数据Id
     * @return 是否删除
     */
    Boolean removeById(Long id);
}
