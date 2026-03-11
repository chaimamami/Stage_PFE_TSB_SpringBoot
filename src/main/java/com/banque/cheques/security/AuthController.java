package com.banque.cheques.security;

import com.banque.cheques.dto.LdapUserInfoDto;
import com.banque.cheques.dto.LoginResponseDto;
import com.banque.cheques.service.AppRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LdapAuthService ldapAuthService;
    private final AppRoleService appRoleService;
    private final JwtService jwtService;
    private final TokenSessionService tokenSessionService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            boolean authenticated = ldapAuthService.authenticate(
                    request.getUsername(),
                    request.getPassword()
            );

            if (!authenticated) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Username ou mot de passe incorrect"));
            }

            LdapUserInfoDto userInfo = ldapAuthService.getUserInfo(request.getUsername());

            if (userInfo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Utilisateur LDAP introuvable"));
            }

            userInfo.setUsername(request.getUsername());

            List<String> appRoles = appRoleService.getUserAppRoles(request.getUsername());
            String targetDashboard = appRoleService.resolveTargetDashboard(appRoles);

            String accessToken = jwtService.generateAccessToken(
                    request.getUsername(),
                    appRoles,
                    targetDashboard
            );

            String refreshToken = jwtService.generateRefreshToken(
                    request.getUsername(),
                    appRoles
            );

            tokenSessionService.saveRefreshToken(request.getUsername(), refreshToken);

            LoginResponseDto response = LoginResponseDto.builder()
                    .username(userInfo.getUsername())
                    .dn(userInfo.getDn())
                    .cn(userInfo.getCn())
                    .displayName(userInfo.getDisplayName())
                    .mail(userInfo.getMail())
                    .ldapGroups(userInfo.getMemberOf())
                    .appRoles(appRoles)
                    .targetDashboard(targetDashboard)
                    .token(accessToken) // compatibilité
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .accessTokenExpiresIn(jwtService.getAccessTokenExpirationSeconds())
                    .refreshTokenExpiresIn(jwtService.getRefreshTokenExpirationSeconds())
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Username ou mot de passe incorrect"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest request) {
        try {
            String refreshToken = request.getRefreshToken();

            if (refreshToken == null || refreshToken.isBlank()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Refresh token manquant"));
            }

            String username = jwtService.extractUsername(refreshToken);

            if (!jwtService.isRefreshTokenValid(refreshToken, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Refresh token invalide ou expiré"));
            }

            if (!tokenSessionService.isRefreshTokenStoredAndValid(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Refresh token révoqué ou introuvable"));
            }

            List<String> roles = jwtService.extractRoles(refreshToken);
            String targetDashboard = appRoleService.resolveTargetDashboard(roles);

            String newAccessToken = jwtService.generateAccessToken(username, roles, targetDashboard);

            return ResponseEntity.ok(Map.of(
                    "accessToken", newAccessToken,
                    "token", newAccessToken,
                    "accessTokenExpiresIn", jwtService.getAccessTokenExpirationSeconds(),
                    "username", username,
                    "appRoles", roles,
                    "targetDashboard", targetDashboard
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Refresh token invalide ou expiré"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody(required = false) RefreshTokenRequest request
    ) {
        try {
            if (request != null && request.getRefreshToken() != null && !request.getRefreshToken().isBlank()) {
                tokenSessionService.revokeRefreshToken(request.getRefreshToken());
            }

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String accessToken = authHeader.substring(7);
                tokenSessionService.blacklistAccessToken(accessToken);
            }

            return ResponseEntity.ok(Map.of("message", "Déconnexion réussie"));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("message", "Déconnexion effectuée"));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Token manquant"));
            }

            String token = authHeader.substring(7);

            if (!jwtService.isAccessToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Type de token invalide"));
            }

            if (tokenSessionService.isBlacklisted(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Token blacklisté"));
            }

            String username = jwtService.extractUsername(token);
            List<String> roles = jwtService.extractRoles(token);

            String targetDashboard = appRoleService.resolveTargetDashboard(roles);
            LdapUserInfoDto userInfo = ldapAuthService.getUserInfo(username);

            LoginResponseDto response = LoginResponseDto.builder()
                    .username(username)
                    .dn(userInfo != null ? userInfo.getDn() : null)
                    .cn(userInfo != null ? userInfo.getCn() : null)
                    .displayName(userInfo != null ? userInfo.getDisplayName() : null)
                    .mail(userInfo != null ? userInfo.getMail() : null)
                    .ldapGroups(userInfo != null ? userInfo.getMemberOf() : Collections.emptyList())
                    .appRoles(roles)
                    .targetDashboard(targetDashboard)
                    .token(token)
                    .accessToken(token)
                    .accessTokenExpiresIn(jwtService.getAccessTokenExpirationSeconds())
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Token invalide ou expiré"));
        }
    }
}