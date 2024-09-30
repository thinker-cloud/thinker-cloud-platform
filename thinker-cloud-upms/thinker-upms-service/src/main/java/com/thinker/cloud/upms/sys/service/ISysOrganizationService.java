package com.thinker.cloud.upms.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thinker.cloud.upms.api.sys.model.dto.SysOrganizationDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysOrganizationQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysOrganizationVO;
import com.thinker.cloud.upms.sys.model.entity.SysOrganization;

import java.util.List;

/**
 * 组织架构 服务类
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
public interface ISysOrganizationService extends IService<SysOrganization> {

    /**
     * 根据query分页查询
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 数据列表
     */
    List<SysOrganizationVO> page(IPage<SysOrganizationVO> page, SysOrganizationQuery query);

    /**
     * 根据query查询
     *
     * @param query 查询条件
     * @return 数据列表
     */
    List<SysOrganizationVO> list(SysOrganizationQuery query);

    /**
     * 根据query查询ids
     *
     * @param query 查询条件
     * @return List<Long>
     */
    List<Long> idsByQuery(SysOrganizationQuery query);

    /**
     * 根据query统计数量
     *
     * @param query 查询条件
     * @return Integer
     */
    Integer countByQuery(SysOrganizationQuery query);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return SysOrganizationVO
     */
    SysOrganizationVO findDetail(Long id);

    /**
     * 保存,逻辑处理
     *
     * @param dto 数据实体
     * @return 是否保存
     */
    Boolean saveData(SysOrganizationDTO dto);

    /**
     * 根据id修改
     *
     * @param dto 数据实体
     * @return 是否保存
     */
    Boolean modifyById(SysOrganizationDTO dto);

    /**
     * 根据id删除,逻辑处理
     *
     * @param id 数据Id
     * @return 是否删除
     */
    Boolean removeById(Long id);

    /**
     * 根据父节点id获取本级及子级ids
     *
     * @param parentId 父节点id
     * @return List<Long>
     */
    List<Long> listAndSubIds(Long parentId);

    /**
     * 根据query获取本级及子级ids
     *
     * @param query query
     * @return List<Long>
     */
    List<Long> listAndSubIds(SysOrganizationQuery query);

    /**
     * 根据父节点id获取本级及子级
     *
     * @param parentId 父节点id
     * @return List<Long>
     */
    List<SysOrganizationVO> listAndSubList(Long parentId);

    /**
     * 根据query获取本级及子级
     *
     * @param query query
     * @return List<Long>
     */
    List<SysOrganizationVO> listAndSubList(SysOrganizationQuery query);
}
