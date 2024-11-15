package com.thinker.cloud.upms.sys.client;

import com.thinker.cloud.upms.api.sys.client.IMemberClient;
import com.thinker.cloud.upms.api.uac.model.AuthParams;
import com.thinker.cloud.upms.api.uac.model.AuthUserDetail;
import com.thinker.cloud.upms.uac.handler.AuthLoginHandlerFactory;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 会员对外API相关接口实现
 *
 * @author admin
 **/
@DubboService
@AllArgsConstructor
public class MemberClient implements IMemberClient {

    private final AuthLoginHandlerFactory loginHandlerFactory;

    @Override
    public AuthUserDetail getMemberUser(AuthParams params) {
        return loginHandlerFactory.get(params.getAuthType()).getMemberUser(params);
    }
}
