package com.intellihire.authService.repository;

import com.intellihire.authService.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User , UUID>
{

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
