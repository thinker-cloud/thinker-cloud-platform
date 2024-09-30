package com.thinker.cloud.upms.sys.client;

import com.thinker.cloud.upms.api.sys.client.IUserClient;
import com.thinker.cloud.upms.api.uac.model.AuthParams;
import com.thinker.cloud.upms.api.uac.model.AuthUser;
import com.thinker.cloud.upms.uac.handler.AuthLoginHandlerFactory;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 用户对外API相关接口实现
 *
 * @author admin
 **/
@DubboService
@AllArgsConstructor
public class UserClient implements IUserClient {

    private final AuthLoginHandlerFactory loginHandlerFactory;

    @Override
    public AuthUser getAuthUser(AuthParams params) {
        return loginHandlerFactory.get(params.getAuthType()).getAuthUser(params);
    }

}
