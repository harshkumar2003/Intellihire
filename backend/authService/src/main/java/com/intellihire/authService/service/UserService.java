package com.intellihire.authService.service;

import com.intellihire.authService.DTO.Request.LoginRequest;
import com.intellihire.authService.DTO.Request.RegisterRequest;
import com.intellihire.authService.DTO.Response.AuthResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService
{

    public void register(RegisterRequest registerRequest);
    public AuthResponse login(LoginRequest loginRequest,HttpServletResponse response);

}
