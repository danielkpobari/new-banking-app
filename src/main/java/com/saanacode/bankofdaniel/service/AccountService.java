package com.saanacode.bankofdaniel.service;


import com.saanacode.bankofdaniel.dto.request.CreateAccountRequest;
import com.saanacode.bankofdaniel.entity.Account;
import com.saanacode.bankofdaniel.exception.AccountServiceException;
import com.saanacode.bankofdaniel.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import static com.saanacode.bankofdaniel.dto.ErrorCode.ACCOUNT_DOES_NOT_EXIST;
import static com.saanacode.bankofdaniel.dto.ErrorCode.EMAIL_ALREADY_IN_USE;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class AccountService {

    private static final Logger logger = LogManager.getLogger(AccountService.class);

    private final AccountRepository accountRepository;

    public Account createAccount(CreateAccountRequest request) {
        try {
            validateRequest(request);
            return accountRepository.save(
                    new Account(request.getFirstname(), request.getLastname(), request.getEmail())
            );
        } catch (Exception e) {
            logger.error(
                    format("An error occurred while creating Account. Possible reasons: %s", e.getLocalizedMessage()));
            if(e instanceof  AccountServiceException) {
                throw e;
            }
            throw new AccountServiceException(
                    "Account could not be created. Please contact support", INTERNAL_SERVER_ERROR);
        }
    }

    private void validateRequest(CreateAccountRequest request) {
        accountRepository.findByEmail(request.getEmail()).ifPresent(acc -> {
            throw new AccountServiceException(
                    "email is already in use, please update and try again", BAD_REQUEST, EMAIL_ALREADY_IN_USE);
        });
    }

    public Account retrieveAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() ->
             new AccountServiceException("Invalid account id", NOT_FOUND, ACCOUNT_DOES_NOT_EXIST)
        );
    }
}
