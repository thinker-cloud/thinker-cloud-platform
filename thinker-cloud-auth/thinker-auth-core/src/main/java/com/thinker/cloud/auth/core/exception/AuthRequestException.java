package com.thinker.cloud.auth.core.exception;

import com.thinker.cloud.security.exception.AbstractAuthenticationException;

/**
 * 授权请求异常
 *
 * @author admin
 */
public class AuthRequestException extends AbstractAuthenticationException {

    public AuthRequestException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthRequestException(String msg) {
        super(msg);
    }
}
