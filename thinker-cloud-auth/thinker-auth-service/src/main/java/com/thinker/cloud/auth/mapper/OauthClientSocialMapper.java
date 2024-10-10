package com.thinker.cloud.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.auth.api.model.query.OauthClientSocialQuery;
import com.thinker.cloud.auth.api.model.vo.OauthClientSocialVO;
import com.thinker.cloud.auth.model.entity.OauthClientSocial;
import com.thinker.cloud.db.dynamic.datasource.annotation.Slave;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * oauth2客户端社交账号配置
 *
 * @author admin
 * @since 2024-10-10 10:58:49
 */
@Mapper
public interface OauthClientSocialMapper extends BaseMapper<OauthClientSocial> {

    /**
     * 根据query分页查询
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 分页列表
     */
    @Slave
    List<OauthClientSocialVO> page(@Param("page") IPage<OauthClientSocialVO> page, @Param("query") OauthClientSocialQuery query);

    /**
     * 根据query查询列表
     *
     * @param query 查询条件
     * @return 列表
     */
    @Slave
    List<OauthClientSocialVO> list(@Param("query") OauthClientSocialQuery query);

    /**
     * 根据query查询ids
     *
     * @param query 查询条件
     * @return List<Long>
     */
    @Slave
    List<Long> idsByQuery(@Param("query") OauthClientSocialQuery query);

    /**
     * 根据查询条件统计数量
     *
     * @param query 查询条件
     * @return Integer
     */
    @Slave
    Integer countByQuery(@Param("query") OauthClientSocialQuery query);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return OauthClientSocial
     */
    @Slave
    @Override
    OauthClientSocial selectById(@Param("id") Serializable id);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return OauthClientSocialVO
     */
    @Slave
    OauthClientSocialVO findDetail(@Param("id") Long id);
}
