package com.saanacode.bankofdaniel.exception;

import com.yassir.bankservice.dto.ErrorCode;

import java.io.Serial;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class UnAuthorizedException extends YassirException {

    @Serial
    private static final long serialVersionUID = 2894128923758388671L;

    public UnAuthorizedException(String msg) {
        super(msg, UNAUTHORIZED);
    }

    public UnAuthorizedException(String msg, ErrorCode errorCode) {
        super(msg, UNAUTHORIZED, errorCode);
    }
}