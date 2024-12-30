package com.thinker.cloud.auth.core.support;

import cn.hutool.core.util.StrUtil;
import com.thinker.cloud.common.constants.CommonConstants;
import com.thinker.cloud.security.token.AbstractAuthenticationToken;
import com.thinker.cloud.security.utils.AuthUtils;
import com.thinker.cloud.security.utils.OAuth2EndpointUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 自定义模式认证转换器
 *
 * @author admin
 */
public abstract class AbstractAuthenticationConverter<T extends AbstractAuthenticationToken> implements AuthenticationConverter {

    protected static final String PARAM_SUBJECT_NAME = "subject";

    @Override
    public Authentication convert(HttpServletRequest request) {
        // 检查是否支持此授权类型
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!this.getGrantType().equals(grantType)) {
            return null;
        }

        // 获取客户端凭证信息
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
        if (clientPrincipal == null) {
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST
                    , "客户端异常", OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI));
        }

        // 获取请求参数
        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);

        // 创建 Authentication Token
        AbstractAuthenticationToken authentication = this.buildAuthenticationToken(parameters);
        authentication.setScopes(getRequestScopes(parameters));

        // 客户端认证类型
        String clientAuthType = AuthUtils.getAuthType(request);
        Map<String, Object> additionalParameters = getAdditionalParameters(parameters);
        additionalParameters.put(CommonConstants.AUTH_TYPE, clientAuthType);
        authentication.setAdditionalParameters(additionalParameters);
        return authentication;
    }

    /**
     * 获取认证类型
     *
     * @return String
     */
    public abstract String getGrantType();

    /**
     * 构建具体类型的token
     *
     * @param parameters 请求参数
     * @return T
     */
    public abstract T buildAuthenticationToken(MultiValueMap<String, String> parameters);

    /**
     * 获取授权范围
     *
     * @param parameters parameters
     * @return Set<String>
     */
    public static Set<String> getRequestScopes(MultiValueMap<String, String> parameters) {
        String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
        if (StrUtil.isNotEmpty(scope)) {
            String[] scopes = StringUtils.delimitedListToStringArray(scope, " ");
            return new HashSet<>(Arrays.asList(scopes));
        }
        return Collections.emptySet();
    }

    /**
     * 获取扩展参数
     *
     * @param parameters parameters
     * @return Map<String, Object>
     */
    private static Map<String, Object> getAdditionalParameters(MultiValueMap<String, String> parameters) {
        return parameters.entrySet().stream().filter(e -> !e.getKey().equals(OAuth2ParameterNames.GRANT_TYPE)
                        && !e.getKey().equals(OAuth2ParameterNames.SCOPE))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));
    }
}
