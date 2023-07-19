package com.saanacode.bankofdaniel.repository;

import com.saanacode.bankofdaniel.entity.TransactionDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {

    Page<TransactionDetails> findTransactionDetailsByWallet_Id(Long walletId, Pageable pageable);

    Page<TransactionDetails> findTransactionDetailsByAccount_Id(Long accountId, Pageable pageable);

}

