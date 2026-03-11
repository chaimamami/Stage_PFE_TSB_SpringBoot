package com.banque.cheques.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class LoginResponseDto {
    private String username;
    private String dn;
    private String cn;
    private String displayName;
    private String mail;
    private List<String> ldapGroups;
    private List<String> appRoles;
    private String targetDashboard;

    private String token;          // garder pour compatibilité = accessToken
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
    private Long refreshTokenExpiresIn;
}