package com.saanacode.bankofdaniel.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminRegistrationRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 66209077143296L;

    @NotBlank(message = "firstname must not be blank")
    @JsonProperty("first_name")
    private String firstname;

    @NotBlank(message = "lastname must not be blank")
    @JsonProperty("last_name")
    private String lastname;

    @Email(message = "please enter a valid email")
    @NotBlank(message = "email must not be blank")
    private String email;

    @NotBlank(message = "password must not be blank")
    @Size(min = 6, message = "password length must not be less than 6")
    private String password;

}
