package com.platform.auth_service.services.impl;

import com.platform.auth_service.configs.JwtConfig;
import com.platform.auth_service.dtos.response.TokenResponseDto;
import com.platform.auth_service.services.IJwtService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * Service for generating JWT tokens.
 */
@Service
public class JwtService implements IJwtService {
    /**
     * Component for get token configs.
     */
    private final JwtConfig jwtConfig;
    /**
     * Key for signing JWT tokens.
     */
    private final Key key;

    /**
     * Constructs a JwtService with the specified JwtConfig.
     *
     * @param jwtConfigParam the configuration properties for JWT
     */
    public JwtService(JwtConfig jwtConfigParam) {
        this.jwtConfig = jwtConfigParam;
        this.key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates a JWT token with the provided claims and user ID.
     *
     * @param userId the unique identifier of the user
     * @param claims a ConcurrentHashMap containing
     *               the claims to be included in the token
     * @return a TokenResponseDto containing
     * the generated JWT token and its expiration details
     */
    @Override
    public TokenResponseDto generateToken(final String userId, final Map<String, String> claims) {
        String token = generateStringToken(userId, claims);
        return new TokenResponseDto(token, jwtConfig.getExpirationSeconds());
    }

    private String generateStringToken(final String userId, final Map<String, String> claims) {
        Instant now = Instant.now();
        JwtBuilder builder = Jwts.builder()
                .setSubject(userId)
                .setIssuer(jwtConfig.getIssuer())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(jwtConfig.getExpirationSeconds())))
                .setClaims(claims);
        return builder.signWith(key, SignatureAlgorithm.HS256).compact();
    }
}
