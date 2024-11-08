package com.thinker.cloud.auth.core.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import com.alibaba.fastjson.JSONObject;
import com.thinker.cloud.core.constants.CommonConstants;
import com.thinker.cloud.core.model.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * 退出登录 ，根据客户端传入跳转
 *
 * @author admin
 */
public class LogoutAuthenticationSuccessHandler implements LogoutSuccessHandler {

    private static final String REDIRECT_URL = "redirect_url";
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        if (response == null) {
            return;
        }

        // 获取请求参数中是否包含 回调地址
        String redirectUrl = request.getParameter(REDIRECT_URL);
        if (StrUtil.isNotBlank(redirectUrl)) {
            redirectStrategy.sendRedirect(request, response, redirectUrl);
            return;
        }

        // 默认跳转referer地址
        if (StrUtil.isNotBlank(request.getHeader(HttpHeaders.REFERER))) {
            String referer = request.getHeader(HttpHeaders.REFERER);
            redirectStrategy.sendRedirect(request, response, referer);
        } else {
            String result = JSONObject.toJSONString(Result.success(null, "登出成功"));
            JakartaServletUtil.write(response, result, CommonConstants.APPLICATION_JSON_UTF8);
        }
    }
}
