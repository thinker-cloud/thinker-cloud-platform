package com.thinker.cloud.auth.support.password;

import com.thinker.cloud.auth.support.base.BaseOAuth2AuthenticationConverter;
import com.thinker.cloud.auth.support.base.BaseOauth2AuthenticationToken;
import com.thinker.cloud.upms.api.uac.enums.AuthTypeEnum;
import org.springframework.util.MultiValueMap;

/**
 * 密码登录认证转换器
 *
 * @author admin
 **/
public class PasswordOAuth2AuthenticationConverter extends BaseOAuth2AuthenticationConverter<BaseOauth2AuthenticationToken> {

    @Override
    public String getGrantType() {
        return AuthTypeEnum.PASSWORD.getValue();
    }

    @Override
    public void checkAuthParams(MultiValueMap<String, String> parameters) {

    }

    @Override
    public BaseOauth2AuthenticationToken genAuthToken(MultiValueMap<String, String> parameters) {
        return null;
    }
}
