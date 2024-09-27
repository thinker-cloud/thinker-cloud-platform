package com.thinker.cloud.gateway.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinker.cloud.core.enums.ResponseCode;
import com.thinker.cloud.core.model.Result;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * 网关异常通用处理器，只作用在webflux 环境下 , 优先级低于 {@link ResponseStatusExceptionHandler} 执行
 *
 * @author admin
 */
@Slf4j
@Order(-1)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class ErrorExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @NonNull
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, @NonNull Throwable e) {
        log.debug("网关异常 : {} {}", exchange.getRequest().getPath(), e.getMessage(), e);

        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(e);
        }

        // header set
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (e instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) e).getStatusCode());
        }

        // response
        return response.writeWith(errorResponse(response, e.getMessage()));
    }

    @SneakyThrows
    private Mono<DataBuffer> errorResponse(ServerHttpResponse response, String errorMessage) {
        DataBufferFactory bufferFactory = response.bufferFactory();
        Integer code = Optional.ofNullable(response.getStatusCode())
                .map(HttpStatusCode::value)
                .orElse(ResponseCode.SERVER_FAILURE.getCode());
        Result<Void> result;
        if (ResponseCode.NOT_FOUND.getCode().equals(code)) {
            result = Result.failure(code, ResponseCode.NOT_FOUND.getDesc());
        } else {
            result = Result.failure(code, errorMessage);
        }
        return Mono.just(bufferFactory.wrap(objectMapper.writeValueAsBytes(result)));
    }
}
