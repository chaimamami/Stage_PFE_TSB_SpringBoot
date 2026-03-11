package com.banque.cheques.security;

import com.banque.cheques.dto.LdapUserInfoDto;
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
import java.util.ArrayList;
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
            System.out.println("LDAP AUTH ERROR = " + e.getMessage());
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
                (AttributesMapper<String>) attrs -> {
                    if (attrs.get("distinguishedName") != null) {
                        Object value = attrs.get("distinguishedName").get();
                        return value != null ? value.toString() : null;
                    }
                    return null;
                }
        );

        return results.isEmpty() ? null : results.get(0);
    }

    public LdapUserInfoDto getUserInfo(String username) {
        EqualsFilter filter = new EqualsFilter("sAMAccountName", username);

        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        String searchBase = ldapConfig.getUserSearchBase();
        if (searchBase == null) {
            searchBase = "";
        }

        List<LdapUserInfoDto> results = ldapTemplate.search(
                searchBase,
                filter.encode(),
                controls,
                (AttributesMapper<LdapUserInfoDto>) this::mapToUserInfo
        );

        return results.isEmpty() ? null : results.get(0);
    }

    private LdapUserInfoDto mapToUserInfo(Attributes attrs) throws NamingException {
        List<String> groups = new ArrayList<>();

        if (attrs.get("memberOf") != null) {
            for (int i = 0; i < attrs.get("memberOf").size(); i++) {
                Object value = attrs.get("memberOf").get(i);
                if (value != null) {
                    groups.add(value.toString());
                }
            }
        }

        return LdapUserInfoDto.builder()
                .dn(getAttribute(attrs, "distinguishedName"))
                .cn(getAttribute(attrs, "cn"))
                .displayName(getAttribute(attrs, "displayName"))
                .mail(getAttribute(attrs, "mail"))
                .memberOf(groups)
                .build();
    }

    private String getAttribute(Attributes attrs, String name) throws NamingException {
        if (attrs.get(name) != null) {
            Object value = attrs.get(name).get();
            return value != null ? value.toString() : null;
        }
        return null;
    }
}