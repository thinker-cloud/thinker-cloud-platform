package com.thinker.cloud.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thinker.cloud.auth.api.model.dto.OauthClientDTO;
import com.thinker.cloud.auth.api.model.query.OauthClientQuery;
import com.thinker.cloud.auth.api.model.vo.OauthClientVO;
import com.thinker.cloud.auth.model.entity.OauthClient;

import java.util.List;

/**
 * oauth2客户端配置 服务类
 *
 * @author admin
 * @since 2024-10-10 10:58:49
 */
public interface IOauthClientService extends IService<OauthClient> {

    /**
     * 根据query分页查询
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 数据列表
     */
    List<OauthClientVO> page(IPage<OauthClientVO> page, OauthClientQuery query);

    /**
     * 根据query查询
     *
     * @param query 查询条件
     * @return 数据列表
     */
    List<OauthClientVO> list(OauthClientQuery query);

    /**
     * 根据query查询ids
     *
     * @param query 查询条件
     * @return List<Long>
     */
    List<Long> idsByQuery(OauthClientQuery query);

    /**
     * 根据query统计数量
     *
     * @param query 查询条件
     * @return Integer
     */
    Integer countByQuery(OauthClientQuery query);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return OauthClientVO
     */
    OauthClientVO findDetail(Long id);

    /**
     * 根据clientId查询详情
     *
     * @param clientId clientId
     * @return OauthClientVO
     */
    OauthClientVO findByClientId(String clientId);

    /**
     * 保存,逻辑处理
     *
     * @param dto 数据实体
     * @return 是否保存
     */
    Boolean saveData(OauthClientDTO dto);

    /**
     * 根据id修改
     *
     * @param dto 数据实体
     * @return 是否保存
     */
    Boolean modifyById(OauthClientDTO dto);

    /**
     * 根据id删除,逻辑处理
     *
     * @param id 数据Id
     * @return 是否删除
     */
    Boolean removeById(Long id);
}
