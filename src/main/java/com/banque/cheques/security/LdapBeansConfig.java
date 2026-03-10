package com.banque.cheques.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@RequiredArgsConstructor
public class LdapBeansConfig {

    private final LdapConfig ldapConfig;

    @Bean
    public LdapContextSource ldapContextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapConfig.getUrl());
        contextSource.setBase(ldapConfig.getBase());
        contextSource.setUserDn(ldapConfig.getBindDn());
        contextSource.setPassword(ldapConfig.getBindPassword());
        contextSource.afterPropertiesSet();
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate(LdapContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }
}