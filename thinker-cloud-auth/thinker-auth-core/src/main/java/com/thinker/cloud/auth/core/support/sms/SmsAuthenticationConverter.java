package com.thinker.cloud.auth.core.support.sms;

import com.thinker.cloud.auth.core.support.AbstractAuthenticationConverter;
import com.thinker.cloud.security.model.AuthParams;
import com.thinker.cloud.security.token.SmsAuthenticationToken;
import com.thinker.cloud.security.utils.OAuth2EndpointUtils;
import org.springframework.util.MultiValueMap;

/**
 * 短信登录认证转换器
 *
 * @author admin
 **/
public class SmsAuthenticationConverter extends AbstractAuthenticationConverter<SmsAuthenticationToken> {

    @Override
    public String getGrantType() {
        return SmsAuthenticationToken.GRANT_TYPE.getValue();
    }

    @Override
    public SmsAuthenticationToken buildAuthenticationToken(MultiValueMap<String, String> parameters) {
        // 获取登录认证请求参数
        String subject = OAuth2EndpointUtils.getParameter(parameters, PARAM_SUBJECT_NAME);
        return new SmsAuthenticationToken(new AuthParams(this.getGrantType(), subject, null));
    }
}
