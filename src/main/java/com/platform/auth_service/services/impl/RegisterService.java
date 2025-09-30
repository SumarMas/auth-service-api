package com.platform.auth_service.services.impl;

import com.platform.auth_service.controllers.manageExceptions.CustomException;
import com.platform.auth_service.dtos.request.RegisterRequestDto;
import com.platform.auth_service.dtos.response.TokenResponseDto;
import com.platform.auth_service.entities.UserCredentials;
import com.platform.auth_service.repositories.UserCredentialsRepository;
import com.platform.auth_service.services.IJwtService;
import com.platform.auth_service.services.IRegisterService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service implementation for user registration.
 * Handles the registration process,
 * including saving user credentials and generating JWT tokens.
 */
@Service
@AllArgsConstructor
public class RegisterService implements IRegisterService {
    /**
     * Logger for logging information and errors.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterService.class);
    /**
     * Repository for accessing user credentials data.
     */
    private final UserCredentialsRepository userCredentialsRepository;
    /**
     * Password encoder for encoding user passwords.
     */
    private final PasswordEncoder passwordEncoder;
    /**
     * Service for handling JWT operations.
     */
    private final IJwtService jwtService;

    /**
     * Registers a new user and generates a JWT token upon successful registration.
     *
     * @param registerRequestDto the registration request
     *                           data transfer object containing user details
     * @return a TokenResponseDto containing the generated JWT token
     * @throws CustomException if registration fails
     *                         or an error occurs during the process
     */
    @Override
    @Transactional
    public TokenResponseDto register(RegisterRequestDto registerRequestDto) {
        if (existsByUsername(registerRequestDto.getUsername())) {
            LOGGER.info("Username already exists");
            throw new CustomException("Username already exists", HttpStatus.CONFLICT);
        }

        UserCredentials userCredentials = buildUserCredentials(registerRequestDto);

        try {
            userCredentialsRepository.save(userCredentials);
            LOGGER.info("User registered successfully with username: {}", registerRequestDto.getUsername());
        } catch (DataAccessException ex) {
            LOGGER.error("Database error while saving user credentials: {}", ex.getMessage());
            throw new CustomException("Error saving user credentials", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
        return getToken(registerRequestDto.getUsername(), registerRequestDto.getDefaultRole());
    }

    private boolean existsByUsername(String username) {
        try {
            return userCredentialsRepository.existsByUsername(username);
        } catch (DataAccessException ex) {
            LOGGER.error("Database error while checking username existence: {}", ex.getMessage());
            throw new CustomException("Error checking username existence", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private UserCredentials buildUserCredentials(RegisterRequestDto registerRequestDto) {
        return UserCredentials.builder()
                .id(UUID.randomUUID())
                .userId(registerRequestDto.getUserId())
                .username(registerRequestDto.getUsername())
                .passwordHash(encodePassword(registerRequestDto.getPassword()))
                .enabled(true)
                .createdUser(registerRequestDto.getUserId())
                .lastUpdatedUser(registerRequestDto.getUserId())
                .build();
    }

    private String encodePassword(String password) {
        try {
            return passwordEncoder.encode(password);
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Error encoding password: {}", ex.getMessage());
            throw new CustomException("Error encoding password", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private TokenResponseDto getToken(String userId, String defaultRole) {
        Map<String, String> claims = new ConcurrentHashMap<>();
        claims.put("role", defaultRole);
        return jwtService.generateToken(userId, claims);
    }
}
