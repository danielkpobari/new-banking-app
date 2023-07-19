package com.saanacode.bankofdaniel.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yassir.bankservice.dto.ApiError;
import com.yassir.bankservice.dto.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

import static com.yassir.bankservice.dto.ErrorCode.*;
import static com.yassir.bankservice.entity.GeneralConstants.HEADER_STRING;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    public JwtAuthenticationEntryPoint(@Qualifier("yassirObjectMapper") ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {

        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType(APPLICATION_JSON_VALUE);

        ErrorCode errorCode = null;
        boolean expiredToken = false;


        httpServletResponse.addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        ApiError error = buildApiError(httpServletRequest, httpServletRequest.getHeader(HEADER_STRING));
        OutputStream out = httpServletResponse.getOutputStream();
        mapper.writeValue(out, error);
        out.flush();
    }

    @NotNull
    private static ApiError buildApiError(HttpServletRequest httpServletRequest, String token) {

        if (StringUtils.isBlank(token)) {
            return new ApiError(UNAUTHORIZED.value(), "Full authentication is required to access this resource",
                    httpServletRequest.getRequestURI(), MISSING_TOKEN);
        } else {
            return new ApiError(UNAUTHORIZED.value(), "Invalid token", httpServletRequest.getRequestURI(), INVALID_TOKEN);
        }
    }
}

