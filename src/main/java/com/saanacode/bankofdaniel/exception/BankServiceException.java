package com.saanacode.bankofdaniel.exception;

import com.yassir.bankservice.dto.ErrorCode;
import org.springframework.http.HttpStatus;

public class BankServiceException extends YassirException {

    public BankServiceException(String msg) {
        super(msg);
    }

    public BankServiceException(String msg, HttpStatus status) {
        super(msg, status);
    }

    public BankServiceException(String msg, HttpStatus status, ErrorCode errorCode) {
        super(msg, status, errorCode);
    }
}
