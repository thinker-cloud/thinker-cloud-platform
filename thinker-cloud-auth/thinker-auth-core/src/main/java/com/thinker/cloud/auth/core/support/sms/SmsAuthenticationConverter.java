package com.thinker.cloud.auth.core.support.sms;

import com.thinker.cloud.auth.core.support.base.BaseAuthenticationConverter;
import com.thinker.cloud.security.utils.OAuth2EndpointUtils;
import com.thinker.cloud.upms.api.uac.model.AuthParams;
import org.springframework.util.MultiValueMap;

/**
 * 短信登录认证转换器
 *
 * @author admin
 **/
public class SmsAuthenticationConverter extends BaseAuthenticationConverter<SmsAuthenticationToken> {

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
