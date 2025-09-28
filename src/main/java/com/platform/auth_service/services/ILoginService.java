package com.platform.auth_service.services;

import com.platform.auth_service.dtos.request.LoginRequestDto;
import com.platform.auth_service.dtos.response.TokenResponseDto;
import org.springframework.stereotype.Service;

/**
 * Service interface for handling user login operations.
 */
@Service
public interface ILoginService {
    /**
     * Authenticates a user and generates a JWT token upon successful login.
     *
     * @param loginRequestDto the login request
     *                        data transfer object containing user credentials
     * @return a TokenResponseDto containing the generated JWT token
     */
    TokenResponseDto login(LoginRequestDto loginRequestDto);
}
