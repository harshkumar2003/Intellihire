package com.intellihire.authService.controller;

import com.intellihire.authService.DTO.Request.LoginRequest;
import com.intellihire.authService.DTO.Request.RefreshTokenRequest;
import com.intellihire.authService.DTO.Request.RegisterRequest;
import com.intellihire.authService.DTO.Response.AuthResponse;
import com.intellihire.authService.model.RefreshToken;
import com.intellihire.authService.model.User;
import com.intellihire.authService.repository.RefreshTokenRepo;
import com.intellihire.authService.security.JwtUtil;
import com.intellihire.authService.service.RefreshTokenService;
import com.intellihire.authService.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class UserController
{
    private final UserService userService;
    private final RefreshTokenRepo refreshTokenRepo;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest)
    {
        userService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest , HttpServletResponse response)
    {
        AuthResponse authResponse = userService.login(loginRequest,response);


        return ResponseEntity.ok(authResponse);
    }
    @GetMapping("/profile")
    public String profile(Authentication authentication) {
        return authentication.getName(); // email
    }




}
