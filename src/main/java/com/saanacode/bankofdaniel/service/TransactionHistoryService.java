package com.saanacode.bankofdaniel.service;

import com.saanacode.bankofdaniel.entity.TransactionDetails;
import com.saanacode.bankofdaniel.repository.TransactionDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {

    private static final Logger logger = LogManager.getLogger(TransactionHistoryService.class);

    private final TransactionDetailsRepository transactionDetailsRepository;


    public Page<TransactionDetails> retrieveWalletTransactions(Integer page, Integer size, Long walletId) {
        return transactionDetailsRepository.findTransactionDetailsByWallet_Id(walletId, getPageable(page, size));
    }

    public Page<TransactionDetails> retrieveAccountTransactionDetails(Integer page, Integer size, Long accountId) {
        return transactionDetailsRepository.findTransactionDetailsByAccount_Id(accountId, getPageable(page, size));
    }

    private Pageable getPageable(Integer page, Integer size){
        size = size < 1 || size > 10 ? 5 : size;
        page = page < 1 ? 1 : page;
        return PageRequest.of(--page, size, Sort.Direction.DESC, "createdAt");
    }
}
