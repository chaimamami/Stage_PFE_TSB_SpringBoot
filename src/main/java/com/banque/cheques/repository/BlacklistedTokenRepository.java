package com.banque.cheques.repository;

import com.banque.cheques.entity.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {

    @Query("select count(b) from BlacklistedToken b where b.token = :token")
    Long countByBlacklistedToken(@Param("token") String token);

    @Query("select count(b) from BlacklistedToken b where b.jti = :jti")
    Long countByBlacklistedJti(@Param("jti") String jti);
}