package com.saanacode.bankofdaniel.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 2021967451873601823L;

    @NotBlank(message = "email must not be blank")
    @Email(message = "please enter a valid email")
    private String email;

    @NotBlank(message = "password must not be blank")
    private String password;
}

