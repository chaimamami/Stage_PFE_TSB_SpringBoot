package com.banque.cheques.security;

import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LdapAuthService {

    private final LdapTemplate ldapTemplate;
    private final LdapConfig ldapConfig;

    public boolean authenticate(String username, String password) {
        String userDn = findUserDn(username);

        if (userDn == null || userDn.isBlank()) {
            return false;
        }

        LdapContextSource userContextSource = new LdapContextSource();
        userContextSource.setUrl(ldapConfig.getUrl());
        userContextSource.setBase(ldapConfig.getBase());
        userContextSource.setUserDn(userDn);
        userContextSource.setPassword(password);

        try {
            userContextSource.afterPropertiesSet();
            DirContext context = userContextSource.getContext(userDn, password);
            context.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String findUserDn(String username) {
        EqualsFilter filter = new EqualsFilter("sAMAccountName", username);

        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        String searchBase = ldapConfig.getUserSearchBase();
        if (searchBase == null) {
            searchBase = "";
        }

        List<String> results = ldapTemplate.search(
                searchBase,
                filter.encode(),
                controls,
                (AttributesMapper<String>) this::extractDn
        );

        return results.isEmpty() ? null : results.get(0);
    }

    private String extractDn(Attributes attrs) throws NamingException {
        if (attrs.get("distinguishedName") != null) {
            Object value = attrs.get("distinguishedName").get();
            return value != null ? value.toString() : null;
        }
        return null;
    }
}