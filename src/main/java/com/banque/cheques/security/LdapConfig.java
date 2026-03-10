package com.banque.cheques.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ldap")
@Getter
@Setter
public class LdapConfig {

    private String url;
    private String base;
    private String bindDn;
    private String bindPassword;
    private String userSearchBase;
}