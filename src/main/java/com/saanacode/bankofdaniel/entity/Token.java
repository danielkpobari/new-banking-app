package com.saanacode.bankofdaniel.entity;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE token SET deleted_at = NOW() WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted_at IS NULL")
@Table(name = "token")
public class Token {

    @Id
    @GenericGenerator(name = "native", strategy = "native")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull(message = "Missing required field token")
    @Column(name = "token", unique = true, nullable = false)
    public String token;

    @NotNull(message = "Missing required field revoked")
    @Column(name = "revoked", nullable = false)
    public boolean revoked;

    @NotNull(message = "Missing required field expired")
    @Column(name = "expired", nullable = false)
    public boolean expired;

    @ToString.Exclude
    @NotNull(message = "Missing required field admin_id")
    @ManyToOne(targetEntity = Admin.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    public Admin admin;

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

    public Token(String token, Admin admin) {
        this.token = token;
        this.admin = admin;
        this.expired = false;
        this.revoked = false;
    }
}
