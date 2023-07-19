package com.saanacode.bankofdaniel.controller;


import com.saanacode.bankofdaniel.dto.request.CreateAccountRequest;
import com.saanacode.bankofdaniel.dto.request.CreateWalletRequest;
import com.saanacode.bankofdaniel.dto.response.AccountResource;
import com.saanacode.bankofdaniel.dto.response.WalletResource;
import com.saanacode.bankofdaniel.service.AccountResourceService;
import com.saanacode.bankofdaniel.service.WalletResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountResourceService accountResourceService;
    private final WalletResourceService walletResourceService;

    @PostMapping(value = "", headers = {"Authorization"},
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountResource> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return new ResponseEntity<>(accountResourceService.createCustomerAccount(request), OK);
    }

    @PostMapping(value = "/{accountId}", headers = {"Authorization"},
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountResource> addNewWallet(@PathVariable("accountId") Long accountId,
                                                        @Valid @RequestBody List<CreateWalletRequest> request) {
        return new ResponseEntity<>(accountResourceService.addNewWallet(accountId, request), OK);
    }

    @GetMapping(value = "/{accountId}", headers = {"Authorization"}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountResource> retrieveAccountDetails(@PathVariable("accountId") Long accountId) {
        return new ResponseEntity<>(accountResourceService.retrieveAccountDetails(accountId), OK);
    }

    @GetMapping(value = "/{accountId}/balances", headers = {"Authorization"}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WalletResource>> retrieveAccountWallets(@PathVariable("accountId") Long accountId) {
        return new ResponseEntity<>(accountResourceService.retrieveAccountWallets(accountId), OK);
    }
}
