package com.saanacode.bankofdaniel.entity;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ofPattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneralConstants {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final long EXPIRATION_TIME = 18000_000;

    public static final int MAX_ACCOUNT_NUMBER = 1000000000;
    public static final String HEADER_STRING = "Authorization";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = ofPattern(DATETIME_FORMAT, Locale.US);
    public static final LocalDateTimeSerializer LOCAL_DATE_TIME_SERIALIZER = new
            LocalDateTimeSerializer(LOCAL_DATE_TIME_FORMATTER);
}
