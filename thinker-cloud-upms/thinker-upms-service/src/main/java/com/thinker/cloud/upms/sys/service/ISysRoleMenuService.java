package com.thinker.cloud.upms.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thinker.cloud.upms.api.sys.model.dto.SysRoleMenuDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysRoleMenuQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysRoleMenuVO;
import com.thinker.cloud.upms.sys.model.entity.SysRoleMenu;

import java.util.List;

/**
 * 角色菜单 服务类
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 根据query分页查询
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 数据列表
     */
    List<SysRoleMenuVO> page(IPage<SysRoleMenuVO> page, SysRoleMenuQuery query);

    /**
     * 根据query查询
     *
     * @param query 查询条件
     * @return 数据列表
     */
    List<SysRoleMenuVO> list(SysRoleMenuQuery query);

    /**
     * 根据query查询ids
     *
     * @param query 查询条件
     * @return List<Long>
     */
    List<Long> idsByQuery(SysRoleMenuQuery query);

    /**
     * 根据query统计数量
     *
     * @param query 查询条件
     * @return Integer
     */
    Integer countByQuery(SysRoleMenuQuery query);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return SysRoleMenuVO
     */
    SysRoleMenuVO findDetail(Long id);

    /**
     * 保存,逻辑处理
     *
     * @param dto 数据实体
     * @return 是否保存
     */
    Boolean saveData(SysRoleMenuDTO dto);

    /**
     * 根据id修改
     *
     * @param dto 数据实体
     * @return 是否保存
     */
    Boolean modifyById(SysRoleMenuDTO dto);

    /**
     * 根据id删除,逻辑处理
     *
     * @param id 数据Id
     * @return 是否删除
     */
    Boolean removeById(Long id);
}
