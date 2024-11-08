package com.thinker.cloud.auth.core.endpoint;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thinker.cloud.auth.api.model.vo.AccessTokenVO;
import com.thinker.cloud.auth.api.model.vo.OauthClientVO;
import com.thinker.cloud.auth.core.service.IOauthClientRepository;
import com.thinker.cloud.auth.core.service.OauthTokenDealService;
import com.thinker.cloud.core.model.Result;
import com.thinker.cloud.security.annotation.Inner;
import com.thinker.cloud.security.exception.OAuthClientException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * token 端点
 *
 * @author admimn
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/token")
public class TokenRevokeEndpoint {

    private final OauthTokenDealService oauthTokenDealService;
    private final IOauthClientRepository oauthClientRepository;

    /**
     * 认证页面
     *
     * @param modelAndView modelAndView
     * @param error        表单登录失败处理回调的错误信息
     * @return ModelAndView
     */
    @GetMapping("/login")
    public ModelAndView require(ModelAndView modelAndView, @RequestParam(required = false) String error) {
        modelAndView.setViewName("ftl/login");
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    /**
     * 确认授权页面
     *
     * @param principal    principal
     * @param clientId     clientId
     * @param state        state
     * @param modelAndView modelAndView
     * @return ModelAndView
     */
    @GetMapping("/confirm_access")
    public ModelAndView confirm(Principal principal, ModelAndView modelAndView,
                                @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                                @RequestParam(OAuth2ParameterNames.STATE) String state) {
        // 获取客户端信息
        OauthClientVO oauthClientVO = oauthClientRepository.loadClientByClientId(clientId);
        Optional.ofNullable(oauthClientVO).orElseThrow(() -> new OAuthClientException("clientId 不合法"));

        Set<String> authorizedScopes = StringUtils.commaDelimitedListToSet(oauthClientVO.getScope());
        modelAndView.addObject("clientId", clientId);
        modelAndView.addObject("state", state);
        modelAndView.addObject("scopeList", authorizedScopes);
        modelAndView.addObject("principalName", principal.getName());
        modelAndView.setViewName("ftl/confirm");
        return modelAndView;
    }

    /**
     * 退出token
     *
     * @param authHeader Authorization
     */
    @DeleteMapping("/logout")
    public Result<Boolean> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        if (StrUtil.isBlank(authHeader)) {
            return Result.failure("退出失败，token 为空");
        }

        String tokenValue = authHeader.replace(OAuth2AccessToken.TokenType.BEARER.getValue(), StrUtil.EMPTY);
        return this.removeToken(tokenValue.trim());
    }

    /**
     * 校验token
     *
     * @param token 令牌
     */
    @SneakyThrows
    @GetMapping("/check_token")
    public void checkToken(String token, HttpServletRequest request, HttpServletResponse response) {
        oauthTokenDealService.checkToken(token, request, response);
    }

    /**
     * 移除token
     *
     * @param token token
     */
    @Inner
    @DeleteMapping("/remove/{token}")
    public Result<Boolean> removeToken(@PathVariable("token") String token) {
        oauthTokenDealService.removeToken(token);
        return Result.success();
    }

    /**
     * 查询token分页列表
     *
     * @param params 分页参数
     * @return Result<Page>
     */
    @Inner
    @PostMapping("/page")
    public Result<Page<AccessTokenVO>> tokenPage(@RequestBody Map<String, Object> params) {
        Integer current = MapUtil.getInt(params, "current");
        Integer size = MapUtil.getInt(params, "size");
        Object username = params.get("username");
        Page<AccessTokenVO> page = new Page<>(current, size);
        return Result.success(oauthTokenDealService.tokenPage(page, username));
    }
}
