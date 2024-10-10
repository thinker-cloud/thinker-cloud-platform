package com.thinker.cloud.auth.social;

import com.thinker.cloud.auth.support.AbstractAuthenticationFilter;
import com.thinker.cloud.security.constants.SecurityConstants;
import com.thinker.cloud.security.utils.OAuth2EndpointUtils;
import com.thinker.cloud.upms.api.uac.model.AuthParams;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;

/**
 * 社交授权验证Filter
 *
 * @author admin
 **/
public class SocialAuthenticationFilter extends AbstractAuthenticationFilter<SocialAuthenticationToken> {

    public SocialAuthenticationFilter() {
        super(SecurityConstants.SOCIAL_TOKEN_URL);
    }

    @Override
    public SocialAuthenticationToken buildAuthenticationToken(MultiValueMap<String, String> parameters) {
        // 获取登录认证请求参数
        String authType = OAuth2EndpointUtils.getParameter(parameters, OAuth2ParameterNames.GRANT_TYPE);
        String subject = OAuth2EndpointUtils.getParameter(parameters, PARAM_SUBJECT_NAME);
        String credential = OAuth2EndpointUtils.getParameter(parameters, PARAM_CREDENTIAL_NAME);
        return new SocialAuthenticationToken(new AuthParams(authType, subject, credential));
    }

}
