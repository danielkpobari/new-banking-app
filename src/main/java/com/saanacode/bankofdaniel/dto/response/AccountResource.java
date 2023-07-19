package com.saanacode.bankofdaniel.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AccountResource implements Serializable {

    @Serial
    private static final long serialVersionUID = 4052357352600342676L;

    @NotNull(message = "id must not be blank")
    private Long id;

    @JsonProperty("first_name")
    @NotBlank(message = "firstname must not be blank")
    private String firstname;

    @JsonProperty("last_name")
    @NotBlank(message = "lastname must not be blank")
    private String lastname;

    @Email(message = "email must not be blank")
    @NotBlank(message = "email must not be blank")
    private String email;

    @JsonProperty("accounts")
    @NotNull(message = "accounts must have a value")
    private List<WalletResource> bankAccounts;

}
