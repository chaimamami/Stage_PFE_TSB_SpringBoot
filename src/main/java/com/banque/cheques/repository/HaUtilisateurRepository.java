package com.banque.cheques.repository;

import com.banque.cheques.entity.HaUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HaUtilisateurRepository extends JpaRepository<HaUtilisateur, String> {
    Optional<HaUtilisateur> findByOraUti(String oraUti);
    @Query(value = """
        SELECT u.ORA_UTI
        FROM HA_UTILISATEUR u
        WHERE u.ORA_UTI = :username
        """, nativeQuery = true)
    Optional<String> findOraUtiByUsername(@Param("username") String username);
}