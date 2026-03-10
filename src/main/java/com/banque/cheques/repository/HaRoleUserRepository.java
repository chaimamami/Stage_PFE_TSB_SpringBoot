package com.banque.cheques.repository;

import com.banque.cheques.entity.HaRoleUser;
import com.banque.cheques.entity.HaRoleUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HaRoleUserRepository extends JpaRepository<HaRoleUser, HaRoleUserId> {

    // récupère un rôle actif pour un utilisateur (DAT_FIN = null)
    Optional<HaRoleUser> findFirstById_OraUtiAndDatFinIsNull(String oraUti);

    @Query(value = """
        SELECT ru.role
        FROM HA_ROLE_USER ru
        WHERE ru.ora_uti = :oraUti
          AND ru.dat_debut <= SYSDATE
          AND (ru.dat_fin IS NULL OR ru.dat_fin >= SYSDATE)
        """, nativeQuery = true)
    List<String> findActiveRolesByOraUti(@Param("oraUti") String oraUti);

}