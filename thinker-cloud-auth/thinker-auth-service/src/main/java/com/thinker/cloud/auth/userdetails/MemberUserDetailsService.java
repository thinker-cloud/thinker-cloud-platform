package com.thinker.cloud.auth.userdetails;

import com.thinker.cloud.upms.api.sys.client.IUserClient;
import com.thinker.cloud.upms.api.uac.model.AuthParams;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 会员用户统一授权接口实现
 *
 * @author admin
 **/
@Component
@AllArgsConstructor
@SuppressWarnings("AlibabaServiceOrDaoClassShouldEndWithImpl")
public class MemberUserDetailsService implements BaseUserDetailsService {

    @DubboReference
    private final IUserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return null;
    }

    @Override
    public UserDetails loadAuthUser(AuthParams authParams) {
        return null;
    }
}
