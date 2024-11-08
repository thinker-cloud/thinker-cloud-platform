package com.thinker.cloud.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.thinker.cloud.auth.api.model.query.OauthClientQuery;
import com.thinker.cloud.auth.api.model.vo.OauthClientVO;
import com.thinker.cloud.auth.model.entity.OauthClient;
import com.thinker.cloud.db.dynamic.datasource.annotation.Slave;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * oauth2客户端配置
 *
 * @author admin
 * @since 2024-10-10 10:58:49
 */
@Mapper
public interface OauthClientMapper extends BaseMapper<OauthClient> {

    /**
     * 根据query分页查询
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return 分页列表
     */
    @Slave
    List<OauthClientVO> page(@Param("page") IPage<OauthClientVO> page, @Param("query") OauthClientQuery query);

    /**
     * 根据query查询列表
     *
     * @param query 查询条件
     * @return 列表
     */
    @Slave
    List<OauthClientVO> list(@Param("query") OauthClientQuery query);

    /**
     * 根据query查询ids
     *
     * @param query 查询条件
     * @return List<Long>
     */
    @Slave
    List<Long> idsByQuery(@Param("query") OauthClientQuery query);

    /**
     * 根据查询条件统计数量
     *
     * @param query 查询条件
     * @return Integer
     */
    @Slave
    Integer countByQuery(@Param("query") OauthClientQuery query);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return OauthClient
     */
    @Slave
    @Override
    OauthClient selectById(@Param("id") Serializable id);

    /**
     * 根据id查询详情
     *
     * @param id 数据Id
     * @return OauthClientVO
     */
    @Slave
    OauthClientVO findDetail(@Param("id") Long id);

    /**
     * 根据clientId查询客户端详情
     *
     * @param clientId clientId
     * @return OauthClientVO
     */
    @Slave
    OauthClientVO selectByClientId(@Param("clientId") String clientId);
}
