package com.saanacode.bankofdaniel.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

public class CustomCommonsRequestLoggingFilter extends CommonsRequestLoggingFilter {

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String uri = request.getRequestURI();
        return  uri.contains("/auth") ||  uri.startsWith("/actuator");
    }
}
