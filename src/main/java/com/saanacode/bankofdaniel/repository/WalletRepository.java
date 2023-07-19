package com.saanacode.bankofdaniel.repository;


import com.saanacode.bankofdaniel.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findWalletByWalletNumber(String walletNumber);

    List<Wallet> findWalletByAccount_Id(Long accountId);

    @Query(value = "SELECT * FROM wallet WHERE id = :walletId AND account_id = :accountId", nativeQuery = true)
    Optional<Wallet> findWalletByIdAndAccountById(@Param("walletId") Long walletId,
                                                   @Param("accountId") Long accountId);

    @Query(value = "SELECT * FROM wallet WHERE wallet_no = :walletNo FOR UPDATE", nativeQuery = true)
    Optional<Wallet> findForUpdateByWalletNumber(@Param("walletNo") String walletNo);
}
