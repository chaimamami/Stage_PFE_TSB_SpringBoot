package com.banque.cheques.service;

import com.banque.cheques.repository.HaRoleUserRepository;
import com.banque.cheques.repository.HaUtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppRoleService {

    private final HaUtilisateurRepository haUtilisateurRepository;
    private final HaRoleUserRepository haRoleUserRepository;

    public List<String> getUserAppRoles(String username) {
        Optional<String> oraUtiOpt = haUtilisateurRepository.findOraUtiByUsername(username);

        if (oraUtiOpt.isEmpty()) {
            return Collections.emptyList();
        }

        return haRoleUserRepository.findActiveRoleCodesByOraUti(oraUtiOpt.get());
    }

    public String resolveTargetDashboard(List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            return "/dashboard/default";
        }

        for (String role : roles) {
            if (role == null) {
                continue;
            }

            String normalized = role.trim().toUpperCase();

            if (normalized.equals("CONS-C")) {
                return "/dashboard/cons-c";
            }

            if (normalized.equals("CONS-AGE")) {
                return "/dashboard/cons-age";
            }
        }

        return "/dashboard/default";
    }
}