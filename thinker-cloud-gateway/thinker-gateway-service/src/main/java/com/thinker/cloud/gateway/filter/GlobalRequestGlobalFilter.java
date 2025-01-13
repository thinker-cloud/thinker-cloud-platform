/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thinker.cloud.gateway.filter;

import com.thinker.cloud.common.constants.CommonConstants;
import com.thinker.cloud.common.enums.AuthTypeEnum;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Objects;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

/**
 * 全局拦截器，作用所有的微服务
 * <p>
 * 1. 对请求头中参数进行处理 from 参数进行清洗 2. 重写StripPrefix = 1,支持全局
 * <p>
 *
 * @author admin
 */
@Component
public class GlobalRequestGlobalFilter implements GlobalFilter, Ordered {

    /**
     * Process the Web request and (optionally) delegate to the next {@code WebFilter}
     * through the given {@link GatewayFilterChain}.
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Void>} to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 清洗请求头中from参数
        ServerHttpRequest request = exchange.getRequest();
        request.mutate().headers(httpHeaders -> {
            httpHeaders.remove(CommonConstants.FROM);

            // 设置请求时间
            String requestStartTime = String.valueOf(System.currentTimeMillis());
            httpHeaders.put(CommonConstants.REQUEST_START_TIME, Collections.singletonList(requestStartTime));
        }).build();


        addOriginalRequestUrl(exchange, request.getURI());
        String rawPath = request.getURI().getRawPath();

        // 非登录授权接口直接放行
        AuthTypeEnum authTypeEnum = AuthTypeEnum.resolverByPath(rawPath);
        if (Objects.isNull(authTypeEnum)) {
            return chain.filter(exchange);
        }

        // 设置登录授权标识
        request.mutate().headers(httpHeaders -> {
            httpHeaders.put(CommonConstants.AUTH_TYPE, Collections.singletonList(authTypeEnum.getValue()));
        }).build();

        // 重定向统一授权路径
        String newPath = rawPath.replace(authTypeEnum.getPath(), AuthTypeEnum.ADMIN.getPath());
        ServerHttpRequest newRequest = request.mutate().path(newPath).build();
        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());
        return chain.filter(exchange.mutate().request(newRequest.mutate().build()).build());
    }

    @Override
    public int getOrder() {
        return 10;
    }

}
