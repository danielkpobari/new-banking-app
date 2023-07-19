package com.saanacode.bankofdaniel.service;

import com.saanacode.bankofdaniel.converter.AccountToResourceConverter;
import com.saanacode.bankofdaniel.dto.request.CreateAccountRequest;
import com.saanacode.bankofdaniel.dto.request.CreateWalletRequest;
import com.saanacode.bankofdaniel.dto.response.AccountResource;
import com.saanacode.bankofdaniel.entity.Account;
import com.saanacode.bankofdaniel.entity.Wallet;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountResourceService {

    private final WalletService walletService;
    private final AccountService accountService;
    private final AccountToResourceConverter accountToResourceConverter;

    @Transactional
    public AccountResource createCustomerAccount(@Valid CreateAccountRequest request) {
        Account account= accountService.createAccount(request);
        List<Wallet> wallets = walletService.createAccountWallets(account, request.getBankAccounts());

        return accountToResourceConverter.convert(account, wallets);
    }

    @Transactional
    public AccountResource addNewWallet(Long accountId, @Valid List<CreateWalletRequest> request) {
        Account account = accountService.retrieveAccount(accountId);
        List<Wallet> wallets = walletService.createAccountWallets(account, request);

        return accountToResourceConverter.convert(account, wallets);
    }

    public AccountResource retrieveAccountDetails(Long accountId) {
        Account account = accountService.retrieveAccount(accountId);
        List<Wallet> wallets = walletService.retrieveAccountWallets(account);

        return accountToResourceConverter.convert(account, wallets);
    }

    public List<WalletResource> retrieveAccountWallets(Long customerId) {
        return retrieveAccountDetails(customerId).getBankAccounts();
    }
}
