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
@SQLDelete(sql = "UPDATE transaction SET deleted_at = NOW() WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted_at IS NULL")
@Table(name = "transaction")
public class Transaction {

    @Id
    @GenericGenerator(name = "native", strategy = "native")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull(message = "Missing required field amount")
    @Column(name = "amount", scale = 9, precision = 18, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.NONE)
    @Column(name = "type", columnDefinition = "ENUM('CREDIT','DEBIT')")
    @NotNull(message = "Operation type type must be specified, may be either 'CREDIT' or 'DEBIT'")
    private OperationType type;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "transaction status must be specified")
    @Column(columnDefinition = "ENUM('PROCESSING','FAILED','SUCCESSFUL')", nullable = false)
    private TransactionStatus status;

    @NotNull(message = "Missing required field currency_code")
    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    @ToString.Exclude
    @NotNull(message = "Missing required field admin_id")
    @ManyToOne(targetEntity = Admin.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private Admin admin;

    @ToString.Exclude
    @NotNull(message = "Missing required field wallet")
    @ManyToOne(targetEntity = Wallet.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;

    @ToString.Exclude
    @NotNull(message = "Missing required field consortWallet")
    @ManyToOne(targetEntity = Wallet.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "consort_wallet_id", referencedColumnName = "id")
    private Wallet consortWallet;

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
