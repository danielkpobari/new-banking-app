package com.saanacode.bankofdaniel.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yassir.bankservice.entity.OperationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class TransactionDetailResource implements Serializable {

    @Serial
    private static final long serialVersionUID = -3276566733945594912L;

    @NotNull(message = "id must not be blank")
    private Long id;

    @NotNull(message = "Operation type type must be specified, may be either 'CREDIT' or 'DEBIT'")
    private OperationType type;

    @Size(min = 10, max = 10, message = "walletNo length must be 10")
    @NotBlank(message = "wallet_no must not be blank")
    @JsonProperty("wallet_no")
    private String walletNo;

    @NotNull(message = "amount deposit must have a value")
    @JsonProperty("old_balance")
    private BigDecimal oldBalance;

    @NotNull(message = "new_balance deposit must have a value")
    @JsonProperty("new_balance")
    private BigDecimal newBalance;

    @NotNull(message = "created_at must not be blank")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @NotNull(message = "transaction must not be blank")
    @JsonProperty("transaction")
    private TransactionResource transactionResource;
}
