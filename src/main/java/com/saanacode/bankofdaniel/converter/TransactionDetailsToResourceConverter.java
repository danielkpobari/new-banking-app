package com.saanacode.bankofdaniel.converter;

import com.yassir.bankservice.dto.response.TransactionDetailResource;
import com.yassir.bankservice.entity.TransactionDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class TransactionDetailsToResourceConverter {

    private final TransactionToResourceConverter transactionToResourceConverter;

    public TransactionDetailResource convert(TransactionDetails transactionDetails) {

        return TransactionDetailResource.builder()
                .id(transactionDetails.getId())
                .type(transactionDetails.getType())
                .oldBalance(transactionDetails.getOldBalance().setScale(2, RoundingMode.HALF_UP))
                .newBalance(transactionDetails.getNewBalance().setScale(2, RoundingMode.HALF_UP))
                .walletNo(transactionDetails.getWallet().getWalletNumber())
                .createdAt(transactionDetails.getCreatedAt())
                .transactionResource(transactionToResourceConverter.convert(transactionDetails.getTransaction()))
                .build();
    }
}
