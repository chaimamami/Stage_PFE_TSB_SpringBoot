package com.banque.cheques.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LdapAuthService ldapAuthService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        boolean authenticated = ldapAuthService.authenticate(
                request.getUsername(),
                request.getPassword()
        );

        if (authenticated) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Authentification LDAP réussie");
            response.put("username", request.getUsername());
            response.put("dn", ldapAuthService.findUserDn(request.getUsername()));
            return ResponseEntity.ok(response);
        }

        Map<String, Object> error = new HashMap<>();
        error.put("message", "Échec authentification LDAP");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}