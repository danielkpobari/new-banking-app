package com.saanacode.bankofdaniel.service;

import com.saanacode.bankofdaniel.entity.Admin;
import com.saanacode.bankofdaniel.exception.DaniBankException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class CredentialService {

    public Admin getAdminAccount() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return  (Admin) authentication.getPrincipal();
        } catch (Exception e) {
            throw new DaniBankException("Could not authenticate request", INTERNAL_SERVER_ERROR);
        }
    }
}
