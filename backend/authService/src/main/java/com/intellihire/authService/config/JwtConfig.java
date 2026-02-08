package com.intellihire.authService.config;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtConfig {

    private String secret;
    private long accessTokenExpiry;
    private long refreshTokenExpiry;

    @PostConstruct
    public void validate() {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT_SECRET not loaded");
        }
        if (accessTokenExpiry <= 0) {
            throw new IllegalStateException("ACCESS_TOKEN_EXP not loaded");
        }
        if (refreshTokenExpiry <= 0) {
            throw new IllegalStateException("Refresh_TOKEN_EXP not loaded");
        }
    }
}
