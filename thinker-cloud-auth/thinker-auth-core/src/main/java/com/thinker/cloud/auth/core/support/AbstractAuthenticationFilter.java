package com.thinker.cloud.auth.core.support;

import com.thinker.cloud.auth.core.exception.AuthRequestException;
import com.thinker.cloud.security.component.Oauth2AuthExceptionEntryPoint;
import com.thinker.cloud.security.utils.OAuth2EndpointUtils;
import com.thinker.cloud.upms.api.uac.enums.LoginTypeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

/**
 * 登录认证Filter
 *
 * @author admin
 **/
@Slf4j
@Setter
public abstract class AbstractAuthenticationFilter<T extends AbstractAuthenticationToken> extends AbstractAuthenticationProcessingFilter {

    protected static final String PARAM_SUBJECT_NAME = "subject";
    protected static final String PARAM_CREDENTIAL_NAME = "credential";

    private AuthenticationEventPublisher authenticationEventPublisher;
    private Oauth2AuthExceptionEntryPoint authExceptionEntryPoint;

    public AbstractAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, HttpMethod.POST.name()));
    }

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        // 检查是否是Post请求
        if (!request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // 获取请求参数
        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);

        // 检查授权方式
        String authType = OAuth2EndpointUtils.getParameter(parameters, OAuth2ParameterNames.GRANT_TYPE);
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.resolver(authType);
        if (Objects.isNull(loginTypeEnum)) {
            throw new AuthRequestException("不支持该登录认证方式");
        }

        try {
            // 构建认证token对象
            T authenticationToken = this.buildAuthenticationToken(parameters);
            authenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));

            // 尝试认证
            Authentication authResult = super.getAuthenticationManager().authenticate(authenticationToken);

            // 设置上下文
            SecurityContextHolder.getContext().setAuthentication(authResult);
            return authResult;
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();

            // 推送认证失败事件
            if (authenticationEventPublisher != null) {
                authenticationEventPublisher.publishAuthenticationFailure(new BadCredentialsException(e.getMessage(), e),
                        new PreAuthenticatedAuthenticationToken("access-token", "N/A"));
            }

            // 认证失败异常统一处理
            if (authExceptionEntryPoint != null) {
                authExceptionEntryPoint.commence(request, response, e);
            }
        }

        return null;
    }

    /**
     * 构建授权认证token对象
     *
     * @param parameters 请求参数
     * @return T
     */
    public abstract T buildAuthenticationToken(MultiValueMap<String, String> parameters);
}
