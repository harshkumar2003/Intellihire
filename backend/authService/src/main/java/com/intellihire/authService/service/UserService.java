package com.intellihire.authService.service;

import com.intellihire.authService.DTO.Request.RegisterRequest;
import jakarta.validation.Valid;

public interface UserService
{

    public void register(RegisterRequest registerRequest);

}
