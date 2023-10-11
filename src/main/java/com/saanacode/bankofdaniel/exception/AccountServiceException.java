package com.saanacode.bankofdaniel.exception;

import com.saanacode.bankofdaniel.dto.ErrorCode;
import org.springframework.http.HttpStatus;

public class AccountServiceException extends DaniBankException {

    public AccountServiceException(String msg) {
        super(msg);
    }

    public AccountServiceException(String msg, HttpStatus status) {
        super(msg, status);
    }

    public AccountServiceException(String msg, HttpStatus status, ErrorCode errorCode) {
        super(msg, status, String.valueOf(errorCode));
    }
}
