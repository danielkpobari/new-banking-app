package com.saanacode.bankofdaniel.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -7274065454081602778L;

    @NotBlank(message = "firstname must not be blank")
    @JsonProperty("first_name")
    private String firstname;

    @NotBlank(message = "lastname must not be blank")
    @JsonProperty("last_name")
    private String lastname;

    @NotBlank(message = "email must not be blank")
    @Email(message = "please enter a valid email address")
    private String email;

    @NotNull(message = "bank account detail must be added")
    @Size(min = 1, message = "bank account detail must be added")
    @JsonProperty("bank_accounts")
    private List<CreateWalletRequest> bankAccounts;

}


