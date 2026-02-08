package com.intellihire.authService.service;

import com.intellihire.authService.model.RefreshToken;
import com.intellihire.authService.model.User;
import com.intellihire.authService.repository.RefreshTokenRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepo refreshTokenRepo;

    @Override
    public RefreshToken createRefreshToken(User user) {

        RefreshToken refreshToken = refreshTokenRepo.findByUser(user)
                .orElse(new RefreshToken());

        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(
                Instant.now().plus(7, ChronoUnit.DAYS)
        );

        return refreshTokenRepo.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepo.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }

    @Override
    public RefreshToken findByToken(String token) {

        return refreshTokenRepo.findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token")
                );
    }
}
