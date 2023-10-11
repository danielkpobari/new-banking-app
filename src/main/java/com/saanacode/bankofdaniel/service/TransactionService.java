package com.saanacode.bankofdaniel.service;

import com.saanacode.bankofdaniel.dto.request.TransferRequest;
import com.saanacode.bankofdaniel.entity.*;
import com.saanacode.bankofdaniel.exception.BankServiceException;
import com.saanacode.bankofdaniel.exception.DaniBankException;
import com.saanacode.bankofdaniel.repository.TransactionDetailsRepository;
import com.saanacode.bankofdaniel.repository.TransactionRepository;
import com.saanacode.bankofdaniel.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

import static com.saanacode.bankofdaniel.dto.ErrorCode.*;
import static com.saanacode.bankofdaniel.entity.OperationType.CREDIT;
import static com.saanacode.bankofdaniel.entity.OperationType.DEBIT;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private static final Logger logger = LogManager.getLogger(TransactionService.class);

    private final WalletService walletService;
    private final WalletRepository walletRepository;
    private final TransactionTemplate transactionTemplate;
    private final TransactionRepository transactionRepository;
    private final TransactionDetailsRepository transactionDetailsRepository;

    public synchronized Transaction process(TransferRequest request,
                                            Admin admin) {
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        return transactionTemplate.execute(jpaTransactionStatus -> {
            Wallet sourceWallet = walletService.retrieveWalletForUpdate(request.getSourceWalletNo());
            Wallet destinationWallet = walletService.retrieveWalletForUpdate(request.getDestinationWalletNo());

            validateRequest(sourceWallet, destinationWallet, request.getAmount());

            BigDecimal sourceAccountOldBalance = sourceWallet.getBalance();
            BigDecimal sourceAccountNewBalance = sourceWallet.getBalance().add(request.getAmount().negate());
            BigDecimal destinationAccountOldBalance = destinationWallet.getBalance();
            BigDecimal destinationAccountNewBalance = destinationWallet.getBalance().add(request.getAmount());

            updateAccountBalance(request, sourceWallet, destinationWallet, sourceAccountNewBalance, destinationAccountNewBalance);

            Transaction transaction = createTransactionRecords(request, admin, sourceWallet, destinationWallet);

            createTransactionDetailsRecords(
                    request.getAmount().negate(), transaction, DEBIT, sourceAccountOldBalance, sourceWallet
            );
            createTransactionDetailsRecords(
                    request.getAmount(), transaction, CREDIT, destinationAccountOldBalance, destinationWallet
            );

            logger.info("Successfully transferred fund from wallet: {} to wallet: {}: amount: {}, currencyCode: {}",
                    sourceWallet.getWalletNumber(), destinationWallet.getWalletNumber(),
                    request.getAmount(), sourceWallet.getCurrencyCode());

            return transaction;
        });
    }

    private void createTransactionDetailsRecords(BigDecimal amount,
                                                 Transaction transaction,
                                                 OperationType type,
                                                 BigDecimal oldBalance,
                                                 Wallet wallet) {
        try {
            transactionDetailsRepository.save(
                    TransactionDetails.builder()
                            .type(type)
                            .amount(amount)
                            .oldBalance(oldBalance)
                            .newBalance(wallet.getBalance())
                            .wallet(wallet)
                            .transaction(transaction)
                            .account(wallet.getAccount())
                            .build()
            );
        } catch (Exception e) {
            String msg = format("An error has occurred while creating transaction detail record: for wallet no: %s: " +
                            "amount: %f. Possible reason(s) : %s",
                    wallet.getWalletNumber(), amount, ExceptionUtils.getRootCauseMessage(e));
            logger.error(msg);
            throw new DaniBankException(
                    "Failed updating account balances", INTERNAL_SERVER_ERROR, ACCOUNT_BALANCE_UPDATE_FAILED);
        }
    }

    private Transaction createTransactionRecords(TransferRequest request,
                                                 Admin admin,
                                                 Wallet sourceWallet,
                                                 Wallet destinationWallet) {
        try {
            return transactionRepository.save(
                    Transaction.builder()
                            .type(DEBIT)
                            .amount(request.getAmount())
                            .status(TransactionStatus.SUCCESSFUL)
                            .currencyCode(sourceWallet.getCurrencyCode())
                            .wallet(sourceWallet)
                            .admin(admin)
                            .consortWallet(destinationWallet)
                            .build()
            );
        } catch (Exception e) {
            String msg = format("An error has occurred while creating transaction record: source wallet no: %s and " +
                            "destination wallet no: %s: Amount: %f. Possible reason(s) : %s",
                    sourceWallet.getWalletNumber(), destinationWallet.getWalletNumber(),
                    request.getAmount().floatValue(),
                    ExceptionUtils.getRootCauseMessage(e));
            logger.error(msg);
            throw new DaniBankException(
                    "Failed updating account balances", INTERNAL_SERVER_ERROR, ACCOUNT_BALANCE_UPDATE_FAILED);
        }
    }

    private void updateAccountBalance(TransferRequest request,
                                      Wallet sourceWallet,
                                      Wallet destinationWallet,
                                      BigDecimal sourceWalletNewBalance,
                                      BigDecimal destinationWalletNewBalance) {
        try {
            sourceWallet.setBalance(sourceWalletNewBalance);
            walletRepository.save(sourceWallet);

            destinationWallet.setBalance(destinationWalletNewBalance);
            walletRepository.save(destinationWallet);
        } catch (Exception e) {
            String msg = format("An error has occurred while updating account balance of source wallet no: %s and " +
                            "destination wallet no: %s: Amount: %f. Possible reason(s) : %s",
                    sourceWallet.getWalletNumber(), destinationWallet.getWalletNumber(),
                    request.getAmount().floatValue(),
                    ExceptionUtils.getRootCauseMessage(e));
            logger.error(msg);
            throw new DaniBankException(
                    "Failed updating account balances", INTERNAL_SERVER_ERROR, ACCOUNT_BALANCE_UPDATE_FAILED);
        }
    }

    private void validateRequest(Wallet sourceWallet, Wallet destinationWallet, BigDecimal amount) {
        if (!sourceWallet.getCurrencyCode().equalsIgnoreCase(destinationWallet.getCurrencyCode())) {
            throw new BankServiceException(
                    "Currency code mismatch. Please check the accounts", BAD_REQUEST, CURRENCY_CODE_MISMATCH);
        }

        if (sourceWallet.getBalance().compareTo(amount) < 0) {
            throw new BankServiceException(
                    "Insufficient funds in the wallet to complete this transaction", BAD_REQUEST, INSUFFICIENT_BALANCE);
        }
    }
}
