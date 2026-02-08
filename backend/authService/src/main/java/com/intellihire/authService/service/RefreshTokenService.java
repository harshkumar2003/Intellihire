package com.intellihire.authService.service;

import com.intellihire.authService.model.RefreshToken;
import com.intellihire.authService.model.User;



public interface RefreshTokenService
{
    RefreshToken createRefreshToken(User user);
    RefreshToken verifyExpiration(RefreshToken refreshToken);
    RefreshToken findByToken(String token);
}
