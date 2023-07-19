package com.saanacode.bankofdaniel.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletResource implements Serializable {

    @Serial
    private static final long serialVersionUID = -1962249303835466231L;

    @NotNull(message = "id must not be blank")
    private Long id;

    @Size(min = 3, max = 3)
    @JsonProperty("currency_code")
    @NotBlank(message = "currency code must not be blank")
    private String currencyCode;

    @JsonProperty("wallet_no")
    @NotNull(message = "walletNumber must have a value")
    private String walletNumber;

    @NotNull(message = "initial deposit must have a value")
    private BigDecimal balance;
}
