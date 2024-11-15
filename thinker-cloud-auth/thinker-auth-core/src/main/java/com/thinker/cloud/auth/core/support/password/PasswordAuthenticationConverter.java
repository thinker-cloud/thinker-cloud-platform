package com.thinker.cloud.auth.core.support.password;

import com.thinker.cloud.auth.core.support.base.BaseAuthenticationConverter;
import com.thinker.cloud.security.utils.OAuth2EndpointUtils;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;

/**
 * 密码登录认证转换器
 *
 * @author admin
 **/
public class PasswordAuthenticationConverter extends BaseAuthenticationConverter<PasswordAuthenticationToken> {

    @Override
    public String getGrantType() {
        return PasswordAuthenticationToken.GRANT_TYPE.getValue();
    }

    @Override
    public PasswordAuthenticationToken buildAuthenticationToken(MultiValueMap<String, String> parameters) {
        String username = OAuth2EndpointUtils.getParameter(parameters, OAuth2ParameterNames.USERNAME);
        String password = OAuth2EndpointUtils.getParameter(parameters, OAuth2ParameterNames.PASSWORD);
        return new PasswordAuthenticationToken(username, password);
    }
}
