package com.saanacode.bankofdaniel.repository;

import com.saanacode.bankofdaniel.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    @Modifying
    @Query(value = "UPDATE token SET expired = true, revoked = true WHERE admin_id = :adminId", nativeQuery = true)
    void invalidateAllAdminTokens(@Param("adminId") Long adminId);
}
