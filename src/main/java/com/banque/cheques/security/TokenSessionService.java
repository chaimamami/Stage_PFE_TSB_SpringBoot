package com.banque.cheques.security;

import com.banque.cheques.entity.BlacklistedToken;
import com.banque.cheques.entity.RefreshToken;
import com.banque.cheques.repository.BlacklistedTokenRepository;
import com.banque.cheques.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenSessionService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final JwtService jwtService;

    public void saveRefreshToken(String username, String refreshToken) {
        RefreshToken entity = new RefreshToken();
        entity.setUsername(username);
        entity.setToken(refreshToken);
        entity.setJti(jwtService.extractJti(refreshToken));
        entity.setExpiryDate(toLocalDateTime(jwtService.extractExpiration(refreshToken)));
        entity.setRevoked(false);
        entity.setCreatedAt(LocalDateTime.now());

        refreshTokenRepository.save(entity);
    }

    public boolean isRefreshTokenStoredAndValid(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken)
                .map(rt -> !rt.isRevoked() && rt.getExpiryDate().isAfter(LocalDateTime.now()))
                .orElse(false);
    }

    public void revokeRefreshToken(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken).ifPresent(rt -> {
            rt.setRevoked(true);
            refreshTokenRepository.save(rt);
        });
    }

    public void blacklistAccessToken(String accessToken) {
        BlacklistedToken entity = new BlacklistedToken();
        entity.setToken(accessToken);
        entity.setJti(jwtService.extractJti(accessToken));
        entity.setUsername(jwtService.extractUsername(accessToken));
        entity.setExpiresAt(toLocalDateTime(jwtService.extractExpiration(accessToken)));
        entity.setCreatedAt(LocalDateTime.now());

        blacklistedTokenRepository.save(entity);
    }

    public boolean isBlacklisted(String token) {
        String jti = jwtService.extractJti(token);

        Long tokenCount = blacklistedTokenRepository.countByBlacklistedToken(token);
        Long jtiCount = (jti != null) ? blacklistedTokenRepository.countByBlacklistedJti(jti) : 0L;

        return (tokenCount != null && tokenCount > 0)
                || (jtiCount != null && jtiCount > 0);
    }

    private LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}