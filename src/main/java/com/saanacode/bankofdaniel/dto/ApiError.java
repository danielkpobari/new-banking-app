package com.saanacode.bankofdaniel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@JsonPropertyOrder(value = {"timeStamp", "message", "path", "errorCode", "infoLink", "details"})
@NoArgsConstructor
public class ApiError {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeStamp;

    @JsonIgnore
    private int status;

    private String message;

    private String path;

    @Setter
    private String errorCode;

    @Setter
    private String infoLink;

    private List<Object> details = new ArrayList<>();

    public ApiError(final int status, final String message, final String path, final ErrorCode errorCode) {
        timeStamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.path = path;
        this.errorCode = (errorCode != null) ? errorCode.toString() : null;
    }

    public ApiError(final int status, final String message, final String path) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.errorCode = status >= 400 && status < 500 ? ErrorCode.INVALID_INPUT_PROVIDED.name() : null;
        timeStamp = LocalDateTime.now();
    }

}
