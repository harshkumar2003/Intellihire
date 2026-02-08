package com.intellihire.authService.controller;

import com.intellihire.authService.DTO.Request.RefreshTokenRequest;
import com.intellihire.authService.DTO.Response.AuthResponse;
import com.intellihire.authService.model.RefreshToken;
import com.intellihire.authService.model.User;
import com.intellihire.authService.security.JwtUtil;
import com.intellihire.authService.service.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(HttpServletRequest request) {

        String refreshToken = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new RuntimeException("Refresh token missing"));

        RefreshToken token = refreshTokenService.findByToken(refreshToken);

        User user = token.getUser();

        String newAccessToken = jwtUtil.generateAccessToken(
                user.getEmail(),
                user.getRole().name()
        );

        return ResponseEntity.ok(new AuthResponse(newAccessToken));
    }
}
