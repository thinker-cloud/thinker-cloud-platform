package com.thinker.cloud.auth.core.userdetails;

import com.thinker.cloud.common.enums.AuthTypeEnum;
import com.thinker.cloud.security.model.AuthParams;
import com.thinker.cloud.upms.api.sys.client.IMemberClient;
import com.thinker.cloud.upms.api.uac.model.AuthUserDetail;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
    private final IMemberClient memberClient;

    @Override
    public AuthTypeEnum getAuthType() {
        return AuthTypeEnum.MEMBER;
    }

    @Override
    public UserDetails loadUserByAuthParams(AuthParams authParams) {
        AuthUserDetail memberUser = memberClient.getMemberUser(authParams);

        Optional.ofNullable(memberUser).orElseThrow(() -> new UsernameNotFoundException("账号不存在"));
        return null;
    }

}
