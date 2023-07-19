package com.saanacode.bankofdaniel.controller;


import com.saanacode.bankofdaniel.dto.request.AdminRegistrationRequest;
import com.saanacode.bankofdaniel.dto.request.AuthenticationRequest;
import com.saanacode.bankofdaniel.dto.response.AuthenticationResponse;
import com.saanacode.bankofdaniel.dto.response.RegistrationResponse;
import com.saanacode.bankofdaniel.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody AdminRegistrationRequest request) {
        authenticationService.register(request);
        return ResponseEntity.ok(new RegistrationResponse("Admin account created successfully"));
    }

    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.OK);
    }

}
