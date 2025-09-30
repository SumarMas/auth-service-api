package com.platform.auth_service.services;

import com.platform.auth_service.dtos.response.TokenResponseDto;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service interface for generating JWT tokens.
 */
@Service
public interface IJwtService {
    /**
     * Generates a JWT token with the provided claims.
     *
     * @param claims a ConcurrentHashMap containing
     *               the claims to be included in the token
     * @param userId the unique identifier of the user
     * @return the generated JWT token and its expiration details
     */
    TokenResponseDto generateToken(String userId, Map<String, String> claims);
}
