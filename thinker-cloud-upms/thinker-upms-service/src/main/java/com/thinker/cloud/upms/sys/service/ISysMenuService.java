package com.thinker.cloud.upms.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thinker.cloud.upms.api.sys.model.dto.SysMenuDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysMenuQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysMenuVO;
import com.thinker.cloud.upms.sys.model.entity.SysMenu;

import java.util.List;

/**
 * 菜单管理 服务类
 *
 * @author admin
 * @since 2024-09-27 17:43:02
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 根据query分页查询
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 数据列表
     */
    List<SysMenuVO> page(IPage<SysMenuVO> page, SysMenuQuery query);

    /**
     * 根据query查询
     *
     * @param query 查询条件
     * @return 数据列表
     */
    List<SysMenuVO> list(SysMenuQuery query);

    /**
     * 根据query查询ids
     *
     * @param query 查询条件
     * @return List<Long>
     */
    List<Long> idsByQuery(SysMenuQuery query);

    /**
     * 根据query统计数量
     *
     * @param query 查询条件
     * @return Integer
     */
    Integer countByQuery(SysMenuQuery query);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return SysMenuVO
     */
    SysMenuVO findDetail(Long id);

    /**
     * 保存,逻辑处理
     *
     * @param dto 数据实体
     * @return 是否保存
     */
    Boolean saveData(SysMenuDTO dto);

    /**
     * 根据id修改
     *
     * @param dto 数据实体
     * @return 是否保存
     */
    Boolean modifyById(SysMenuDTO dto);

    /**
     * 根据id删除,逻辑处理
     *
     * @param id 数据Id
     * @return 是否删除
     */
    Boolean removeById(Long id);

    /**
     * 根据角色ids获取系统菜单
     *
     * @param roleIds roleIds
     * @return List<SysMenuVO>
     */
    List<SysMenuVO> listByRoleIds(List<Long> roleIds);
}
