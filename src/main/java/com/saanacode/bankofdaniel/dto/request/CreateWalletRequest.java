package com.saanacode.bankofdaniel.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CreateWalletRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -7611273296668615101L;

    @Size(min = 3, max = 3)
    @NotBlank(message = "currency code must not be blank")
    @JsonProperty("currency_code")
    private String currencyCode;

    @NotNull(message = "initial deposit must have a value")
    @Positive(message = "initial deposit must be positive")
    @JsonProperty("initial_deposit")
    private BigDecimal initialDeposit;
}
