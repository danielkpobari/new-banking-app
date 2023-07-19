package com.saanacode.bankofdaniel.converter;

import com.yassir.bankservice.dto.response.AccountResource;
import com.yassir.bankservice.entity.Account;
import com.yassir.bankservice.entity.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class AccountToResourceConverter {

    private final WalletToResourceConverter walletToResourceConverter;

    public AccountResource convert(Account account, List<Wallet> wallets) {

        return AccountResource.builder()
                .id(account.getId())
                .firstname(account.getFirstName())
                .lastname(account.getLastName())
                .email(account.getEmail())
                .bankAccounts(wallets.parallelStream().map(walletToResourceConverter::convert).collect(toList()))
                .build();


    }
}
