package com.intellihire.authService.service;

import com.intellihire.authService.DTO.Request.LoginRequest;
import com.intellihire.authService.DTO.Request.RegisterRequest;
import com.intellihire.authService.DTO.Response.AuthResponse;
import com.intellihire.authService.config.PasswordEncoderConfig;
import com.intellihire.authService.exception.EmailAlreadyExistsException;
import com.intellihire.authService.exception.InvalidCredentialsException;
import com.intellihire.authService.model.Provider;
import com.intellihire.authService.enums.RegistrationType;
import com.intellihire.authService.model.RefreshToken;
import com.intellihire.authService.model.Role;
import com.intellihire.authService.model.User;
import com.intellihire.authService.repository.UserRepo;
import com.intellihire.authService.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService
{
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    @Override
    public void register(RegisterRequest registerRequest)
    {
       // Duplicate Check
        if(userRepo.existsByEmail(registerRequest.getEmail()))
        {
            throw new EmailAlreadyExistsException("Email Already Exists");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setProvider(Provider.LOCAL);


        if(registerRequest.getRegistrationType() == RegistrationType.RECRUITER)
        {
            user.setRole(Role.RECRUITER);
        }
        else
        {
            user.setRole(Role.STUDENT);
        }

        userRepo.save(user);
    }



    @Override
    @Transactional
    public AuthResponse login(LoginRequest loginRequest , HttpServletResponse response)
    {
        User user = userRepo.findByEmail(loginRequest.getEmail()).
                orElseThrow(()->new InvalidCredentialsException("Invalid email or password"));

        boolean passwordMatch = passwordEncoder.matches(loginRequest.getPassword(),user.getPassword());
        if(!passwordMatch)
        {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        String accessToken = jwtUtil.generateAccessToken(user.getEmail(),user.getRole().name());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        ResponseCookie cookie = ResponseCookie.from("refreshToken",refreshToken.getToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("strict")
                .path("/api/v1/auth/refresh")
                .maxAge(7*24*60*60)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE,cookie.toString());
        return new AuthResponse(accessToken);
    }
}
