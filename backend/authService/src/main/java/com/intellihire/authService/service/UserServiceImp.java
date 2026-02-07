package com.intellihire.authService.service;

import com.intellihire.authService.DTO.Request.RegisterRequest;
import com.intellihire.authService.model.Provider;
import com.intellihire.authService.model.RegistrationType;
import com.intellihire.authService.model.Role;
import com.intellihire.authService.model.User;
import com.intellihire.authService.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.intellihire.authService.model.Role.RECRUITER;
import static com.intellihire.authService.model.Role.STUDENT;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService
{
    private final UserRepo userRepo;

    @Override
    public void register(RegisterRequest registerRequest)
    {
       // Duplicate Check
        if(userRepo.existsByEmail(registerRequest.getEmail()))
        {
            throw new IllegalArgumentException("Email Already Exists");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
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
}
