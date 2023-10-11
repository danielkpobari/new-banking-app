package com.saanacode.bankofdaniel.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.saanacode.bankofdaniel.entity.TransactionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResource implements Serializable {

    @Serial
    private static final long serialVersionUID = -5681760218540133078L;

    @NotNull(message = "id must not be blank")
    private Long id;

    @NotNull(message = "amount must have a value")
    @Positive(message = "amount must be positive")
    @JsonProperty("amount")
    private BigDecimal amount;

    @NotNull(message = "transaction status must have a value")
    private TransactionStatus status;

    @Size(min = 3, max = 3)
    @NotBlank(message = "currency code must not be blank")
    @JsonProperty("currency_code")
    private String currencyCode;

    @Size(min = 10, max = 10, message = "source_wallet_no length must be 10")
    @NotBlank(message = "source_wallet_no code must not be blank")
    @JsonProperty("source_wallet_no")
    private String sourceWalletNo;

    @Size(min = 10, max = 10, message = "destination_wallet_no length must be 10")
    @NotBlank(message = "destination_wallet_no must not be blank")
    @JsonProperty("destination_wallet_no")
    private String destinationWalletNo;

    @NotNull(message = "authorized_by must not be blank")
    @JsonProperty("authorized_by")
    private AdminResource authorizedBy;

    @NotNull(message = "created_at must not be blank")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
