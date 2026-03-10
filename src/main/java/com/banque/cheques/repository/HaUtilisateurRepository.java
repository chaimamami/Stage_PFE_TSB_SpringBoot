package com.banque.cheques.repository;

import com.banque.cheques.entity.HaUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HaUtilisateurRepository extends JpaRepository<HaUtilisateur, String> {
    Optional<HaUtilisateur> findByOraUti(String oraUti);
}