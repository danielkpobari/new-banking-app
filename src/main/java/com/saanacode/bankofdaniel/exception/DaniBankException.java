package com.saanacode.bankofdaniel.exception;


import com.saanacode.bankofdaniel.dto.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
public class DaniBankException extends RuntimeException {

    protected HttpStatus status;
    private ErrorCode errorCode;
    private String metadata;
    private String infoLink;

    public DaniBankException(final String msg) {
        super(msg);
    }

    public DaniBankException(final String msg, final HttpStatus status) {
        this(msg);
        this.status = status;
    }

    public DaniBankException(final String msg, final HttpStatus status, final ErrorCode errorCode) {
        this(msg, status);
        this.errorCode = errorCode;
    }

    public DaniBankException(final String msg, final HttpStatus status, final String metadata) {
        this(msg, status);
        this.metadata = metadata;
    }

    public DaniBankException(final String msg, final HttpStatus status, final ErrorCode errorCode, final String metadata) {
        this(msg, status, errorCode);
        this.metadata = metadata;
    }
}
