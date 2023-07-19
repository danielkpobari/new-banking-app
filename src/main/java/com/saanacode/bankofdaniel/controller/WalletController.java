package com.saanacode.bankofdaniel.controller;

import com.saanacode.bankofdaniel.dto.response.WalletResource;
import com.saanacode.bankofdaniel.service.WalletResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletResourceService walletResourceService;

    @GetMapping(value = "/{walletId}", headers = {"Authorization"}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletResource> retrieveWalletDetails(@PathVariable("walletId") Long walletId) {
        return new ResponseEntity<>(walletResourceService.retrieveWalletAccount(walletId), OK);
    }

    @GetMapping(value = "/{walletId}/accounts/{accountId}", headers = {"Authorization"}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletResource> retrieveAccountWalletDetails(@PathVariable("walletId") Long walletId,
                                                                       @PathVariable("accountId") Long accountId) {
        return new ResponseEntity<>(walletResourceService.retrieveWalletAccount(walletId, accountId), OK);
    }
}
