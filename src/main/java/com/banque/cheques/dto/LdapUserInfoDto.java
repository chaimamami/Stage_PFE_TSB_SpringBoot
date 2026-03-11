package com.banque.cheques.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LdapUserInfoDto {
    private String username;
    private String dn;
    private String cn;
    private String displayName;
    private String mail;
    private List<String> memberOf;
}