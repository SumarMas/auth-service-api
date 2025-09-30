package com.platform.auth_service.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for JWT (JSON Web Token) settings.
 */
@Component
@ConfigurationProperties(prefix = "security.jwt")
@Data
public class JwtConfig {
    /**
     * Configuration properties for JWT (JSON Web Token) settings.
     */
    private String issuer;
    /**
     * Secret key used for signing JWT tokens.
     */
    private String secret;
    /**
     * Expiration time for JWT tokens in seconds.
     */
    private long expirationSeconds;
}
