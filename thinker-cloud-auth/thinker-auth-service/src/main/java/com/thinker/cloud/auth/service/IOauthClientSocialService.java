package com.thinker.cloud.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thinker.cloud.auth.api.model.dto.OauthClientSocialDTO;
import com.thinker.cloud.auth.api.model.query.OauthClientSocialQuery;
import com.thinker.cloud.auth.api.model.vo.OauthClientSocialVO;
import com.thinker.cloud.auth.model.entity.OauthClientSocial;

import java.util.List;

/**
 * oauth2客户端社交账号配置 服务类
 *
 * @author admin
 * @since 2024-10-10 10:58:49
 */
public interface IOauthClientSocialService extends IService<OauthClientSocial> {

    /**
     * 根据query分页查询
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 数据列表
     */
    List<OauthClientSocialVO> page(IPage<OauthClientSocialVO> page, OauthClientSocialQuery query);

    /**
     * 根据query查询
     *
     * @param query 查询条件
     * @return 数据列表
     */
    List<OauthClientSocialVO> list(OauthClientSocialQuery query);

    /**
     * 根据query查询ids
     *
     * @param query 查询条件
     * @return List<Long>
     */
    List<Long> idsByQuery(OauthClientSocialQuery query);

    /**
     * 根据query统计数量
     *
     * @param query 查询条件
     * @return Integer
     */
    Integer countByQuery(OauthClientSocialQuery query);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return OauthClientSocialVO
     */
    OauthClientSocialVO findDetail(Long id);

    /**
     * 保存,逻辑处理
     *
     * @param dto 数据实体
     * @return 是否保存
     */
    Boolean saveData(OauthClientSocialDTO dto);

    /**
     * 根据id修改
     *
     * @param dto 数据实体
     * @return 是否保存
     */
    Boolean modifyById(OauthClientSocialDTO dto);

    /**
     * 根据id删除,逻辑处理
     *
     * @param id 数据Id
     * @return 是否删除
     */
    Boolean removeById(Long id);
}
