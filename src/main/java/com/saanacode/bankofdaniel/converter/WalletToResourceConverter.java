package com.saanacode.bankofdaniel.converter;


import com.saanacode.bankofdaniel.dto.response.WalletResource;
import com.saanacode.bankofdaniel.entity.Wallet;
import org.springframework.stereotype.Component;

import static java.math.RoundingMode.HALF_UP;

@Component
public class WalletToResourceConverter {

    public WalletResource convert(Wallet wallet) {

        return WalletResource.builder()
                .id(wallet.getId())
                .currencyCode(wallet.getCurrencyCode())
                .walletNumber(wallet.getWalletNumber())
                .balance(wallet.getBalance().setScale(2, HALF_UP))
                .build();
    }
}
