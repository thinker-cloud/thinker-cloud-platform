package com.thinker.cloud.upms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thinker.cloud.upms.api.sys.model.dto.SysParamsDTO;
import com.thinker.cloud.upms.api.sys.model.query.SysParamsQuery;
import com.thinker.cloud.upms.api.sys.model.vo.SysParamsVO;
import com.thinker.cloud.upms.model.entity.SysParams;

import java.util.List;

/**
 * 公共参数 服务类
 *
 * @author admin
 * @since 2024-09-27 17:44:19
 */
public interface ISysParamsService extends IService<SysParams> {

    /**
     * 根据query分页查询
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 数据列表
     */
    List<SysParamsVO> page(IPage<SysParamsVO> page, SysParamsQuery query);

    /**
     * 根据query查询
     *
     * @param query 查询条件
     * @return 数据列表
     */
    List<SysParamsVO> list(SysParamsQuery query);

    /**
     * 根据query查询ids
     *
     * @param query 查询条件
     * @return List<Long>
     */
    List<Long> idsByQuery(SysParamsQuery query);

    /**
     * 根据query统计数量
     *
     * @param query 查询条件
     * @return Integer
     */
    Integer countByQuery(SysParamsQuery query);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return SysParamsVO
     */
    SysParamsVO findDetail(Long id);

    /**
     * 保存,逻辑处理
     *
     * @param dto 数据实体
     * @return 是否保存
     */
    Boolean saveData(SysParamsDTO dto);

    /**
     * 根据id修改
     *
     * @param dto 数据实体
     * @return 是否保存
     */
    Boolean modifyById(SysParamsDTO dto);

    /**
     * 根据id删除,逻辑处理
     *
     * @param id 数据Id
     * @return 是否删除
     */
    Boolean removeById(Long id);
}
