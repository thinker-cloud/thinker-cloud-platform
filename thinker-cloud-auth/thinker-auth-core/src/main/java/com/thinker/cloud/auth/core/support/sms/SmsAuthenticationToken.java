package com.thinker.cloud.auth.core.support.sms;

import com.thinker.cloud.auth.core.support.base.BaseAuthenticationToken;
import com.thinker.cloud.upms.api.uac.enums.LoginTypeEnum;
import com.thinker.cloud.upms.api.uac.model.AuthParams;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * 短信登录认证转换器
 *
 * @author admin
 **/
public class SmsAuthenticationToken extends BaseAuthenticationToken {

    public static final AuthorizationGrantType GRANT_TYPE = new AuthorizationGrantType(LoginTypeEnum.SMS.getValue());

    public SmsAuthenticationToken(AuthParams authParams) {
        super(GRANT_TYPE, authParams);
    }

    public SmsAuthenticationToken(AuthorizationGrantType grantType, UserDetails principal) {
        super(grantType, principal, principal.getAuthorities());
    }
}
