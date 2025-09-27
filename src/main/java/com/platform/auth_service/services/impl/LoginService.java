package com.platform.auth_service.services.impl;

import com.platform.auth_service.controllers.manageExceptions.CustomException;
import com.platform.auth_service.dtos.common.UserDto;
import com.platform.auth_service.dtos.request.LoginRequestDto;
import com.platform.auth_service.dtos.response.TokenResponseDto;
import com.platform.auth_service.entities.UserCredentials;
import com.platform.auth_service.repositories.UserCredentialsRepository;
import com.platform.auth_service.services.IJwtService;
import com.platform.auth_service.services.ILoginService;
import com.platform.auth_service.services.IUserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class LoginService implements ILoginService {

    /** Logger for logging information and errors. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    /** Service for handling JWT operations. */
    private final IJwtService jwtService;
    /** Service for managing user-related operations. */
    private final IUserService userService;
    /** Repository for accessing user credentials data. */
    private final UserCredentialsRepository userCredentialsRepository;
    /** Password encoder for validating user passwords. */
    private final PasswordEncoder passwordEncoder;

    /**
     * Authenticates a user and generates a JWT token upon successful login.
     *
     * @param loginRequestDto the login request data transfer object containing user credentials
     * @return a TokenResponseDto containing the generated JWT token
     * @throws CustomException if authentication fails or an error occurs during the process
     */
    @Override
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        LOGGER.trace("Login request received");
        UserCredentials userCredentials = getUserCredentialsByUsername(loginRequestDto.getUsername());
        isPasswordValid(loginRequestDto.getPassword(), userCredentials.getPasswordHash());
        UserDto userDto = getUserDtoByUserId(userCredentials.getUserId().toString());
        TokenResponseDto token = generateToken(userDto);
        LOGGER.info("User '{}' logged in successfully.", loginRequestDto.getUsername());
        return token;
    }

    private UserCredentials getUserCredentialsByUsername(String username) {
        try {
            Optional<UserCredentials> optionalUserCredentials = userCredentialsRepository
                    .findUserCredentialsByUsernameAndEnabledIsTrue(username);
            if (optionalUserCredentials.isEmpty()) {
                LOGGER.warn("Login attempt failed: User '{}' not found or not enabled.", username);
                throw new CustomException("Invalid username or password", HttpStatus.UNAUTHORIZED);
            }
            return optionalUserCredentials.get();
        } catch (DataAccessException ex) {
            LOGGER.error("Database error while retrieving user credentials for username '{}': {}", username, ex.getMessage());
            throw new CustomException("Error retrieving user credentials", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private void isPasswordValid(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            LOGGER.warn("Login attempt failed: Invalid password.");
            throw new CustomException("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    private UserDto getUserDtoByUserId(String userId) {
        return userService.getUserByUserId(userId);
    }

    private TokenResponseDto generateToken(UserDto userDto) {
        ConcurrentHashMap<String, String> claims = buildClaims(userDto);
        return jwtService.generateToken(userDto.getUserId().toString(), claims);
    }

    private ConcurrentHashMap<String, String> buildClaims(UserDto userDto) {
        ConcurrentHashMap<String, String> claims = new ConcurrentHashMap<>();
        claims.put("userId", userDto.getUserId().toString());
        claims.put("roles", userDto.getRoles().toString());
        return claims;
    }
}
