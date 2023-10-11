package com.saanacode.bankofdaniel.converter;


import com.saanacode.bankofdaniel.dto.response.AdminResource;
import com.saanacode.bankofdaniel.dto.response.TransactionResource;
import com.saanacode.bankofdaniel.entity.Admin;
import com.saanacode.bankofdaniel.entity.Transaction;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class TransactionToResourceConverter {

    public TransactionResource convert(Admin admin,
                                       String sourceWalletNo,
                                       String destinationWalletNo,
                                       Transaction transaction) {

        return TransactionResource.builder()
                .id(transaction.getId() )
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .currencyCode(transaction.getCurrencyCode())
                .sourceWalletNo(sourceWalletNo)
                .destinationWalletNo(destinationWalletNo)
                .authorizedBy(AdminToResourceConverter.convert(admin))
                .createdAt(transaction.getCreatedAt())
                .build();
    }

    public TransactionResource convert(Transaction transaction) {

        return TransactionResource.builder()
                .id(transaction.getId() )
                .amount(transaction.getAmount().setScale(2, RoundingMode.HALF_UP))
                .status(transaction.getStatus())
                .currencyCode(transaction.getCurrencyCode())
                .sourceWalletNo(transaction.getWallet().getWalletNumber())
                .destinationWalletNo(transaction.getConsortWallet().getWalletNumber())
                .authorizedBy(AdminToResourceConverter.convert(transaction.getAdmin()))
                .createdAt(transaction.getCreatedAt())
                .build();
    }

     static class AdminToResourceConverter {

        public static @NotNull(message = "authorized_by must not be blank") AdminResource convert(Admin admin) {

            return AdminResource.builder()
                    .id(admin.getId())
                    .firstname(admin.getFirstName())
                    .lastname(admin.getLastName())
                    .build();
        }
    }
}
