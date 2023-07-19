package com.saanacode.bankofdaniel.entity;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE transaction_detail SET deleted_at = NOW() WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted_at IS NULL")
@Table(name = "transaction_detail")
public class TransactionDetails {

    @Id
    @GenericGenerator(name = "native", strategy = "native")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull(message = "Missing required field amount")
    @Column(name = "amount", scale = 9, precision = 18, nullable = false)
    private BigDecimal amount;

    @NotNull(message = "Missing required field old_balance")
    @Column(name = "old_balance", scale = 9, precision = 18, nullable = false)
    private BigDecimal oldBalance;

    @NotNull(message = "Missing required field new_balance")
    @Column(name = "new_balance", scale = 9, precision = 18, nullable = false)
    private BigDecimal newBalance;

    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.NONE)
    @Column(name = "type", columnDefinition = "ENUM('CREDIT','DEBIT')")
    @NotNull(message = "Operation type type must be specified, may be either 'CREDIT' or 'DEBIT'")
    private OperationType type;

    @ToString.Exclude
    @NotNull(message = "Missing required field wallet")
    @ManyToOne(targetEntity = Wallet.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;

    @ToString.Exclude
    @NotNull(message = "Missing required field transaction")
    @ManyToOne(targetEntity = Transaction.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Transaction transaction;

    @ToString.Exclude
    @NotNull(message = "Missing required field account_id")
    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    @Setter(AccessLevel.NONE)
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}

