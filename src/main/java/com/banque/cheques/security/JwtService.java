package com.banque.cheques.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "1234567890123456789012345678901212345678901234567890123456789012";

    private static final long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 15;      // 15 min
    private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24; // 24h

    public String generateAccessToken(String username, List<String> roles, String targetDashboard) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("targetDashboard", targetDashboard);
        claims.put("tokenType", "ACCESS");
        claims.put("jti", UUID.randomUUID().toString());

        return buildToken(username, claims, ACCESS_TOKEN_EXPIRATION);
    }

    public String generateRefreshToken(String username, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("tokenType", "REFRESH");
        claims.put("jti", UUID.randomUUID().toString());

        return buildToken(username, claims, REFRESH_TOKEN_EXPIRATION);
    }

    private String buildToken(String username, Map<String, Object> claims, long expirationMs) {
        Date now = new Date();
        Date expiry = new Date(System.currentTimeMillis() + expirationMs);

        return Jwts.builder()
                .setSubject(username)
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public List<String> extractRoles(String token) {
        Object roles = extractAllClaims(token).get("roles");
        if (roles instanceof List<?>) {
            return ((List<?>) roles).stream()
                    .map(String::valueOf)
                    .toList();
        }
        return Collections.emptyList();
    }

    public String extractTokenType(String token) {
        Object tokenType = extractAllClaims(token).get("tokenType");
        return tokenType != null ? tokenType.toString() : null;
    }

    public String extractJti(String token) {
        Object jti = extractAllClaims(token).get("jti");
        return jti != null ? jti.toString() : null;
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isAccessToken(String token) {
        return "ACCESS".equals(extractTokenType(token));
    }

    public boolean isRefreshToken(String token) {
        return "REFRESH".equals(extractTokenType(token));
    }

    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    public boolean isAccessTokenValid(String token, String username) {
        return isTokenValid(token, username) && isAccessToken(token);
    }

    public boolean isRefreshTokenValid(String token, String username) {
        return isTokenValid(token, username) && isRefreshToken(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public long getAccessTokenExpirationSeconds() {
        return ACCESS_TOKEN_EXPIRATION / 1000;
    }

    public long getRefreshTokenExpirationSeconds() {
        return REFRESH_TOKEN_EXPIRATION / 1000;
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(
                Base64.getEncoder().encodeToString(SECRET_KEY.getBytes())
        );
        return Keys.hmacShaKeyFor(keyBytes);
    }
}