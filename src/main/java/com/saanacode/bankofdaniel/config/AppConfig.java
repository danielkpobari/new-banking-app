package com.saanacode.bankofdaniel.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.saanacode.bankofdaniel.filter.CustomCommonsRequestLoggingFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.text.SimpleDateFormat;

import static com.saanacode.bankofdaniel.entity.GeneralConstants.DATETIME_FORMAT;
import static com.saanacode.bankofdaniel.entity.GeneralConstants.LOCAL_DATE_TIME_SERIALIZER;

@Configuration
public class AppConfig {


    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CustomCommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(100000);
        filter.setIncludeHeaders(true);
        filter.setHeaderPredicate(h -> !h.equalsIgnoreCase(HttpHeaders.AUTHORIZATION));
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        filter.setIncludeClientInfo(true);
        return filter;
    }

    @Bean("yassirObjectMapper")
    public ObjectMapper objectMapper(@Qualifier("yassirJavaTimeModule") JavaTimeModule javaTimeModule) {
        return JsonMapper.builder().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .serializationInclusion(JsonInclude.Include.ALWAYS)
                .defaultDateFormat(new SimpleDateFormat(DATETIME_FORMAT))
                .addModule(javaTimeModule).build();
    }

    @Bean("yassirJavaTimeModule")
    public JavaTimeModule javaTimeModule() {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LOCAL_DATE_TIME_SERIALIZER);
        return module;
    }

}
