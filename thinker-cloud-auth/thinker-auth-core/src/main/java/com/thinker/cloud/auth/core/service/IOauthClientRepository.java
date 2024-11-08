package com.thinker.cloud.auth.core.service;

import com.thinker.cloud.auth.api.model.vo.OauthClientVO;

/**
 * oauth2客户端配置
 *
 * @author admin
 **/
public interface IOauthClientRepository {

    /**
     * 根据clientId获取客户端详情
     *
     * @param clientId clientId
     * @return OauthClientVO
     */
    OauthClientVO loadClientByClientId(String clientId);

}
