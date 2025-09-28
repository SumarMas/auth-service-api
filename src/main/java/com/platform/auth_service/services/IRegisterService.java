package com.platform.auth_service.services;

import com.platform.auth_service.dtos.request.RegisterRequestDto;
import com.platform.auth_service.dtos.response.TokenResponseDto;
import org.springframework.stereotype.Service;

/**
 * Service interface for user registration.
 */
@Service
public interface IRegisterService {
    /**
     * Registers a new user based on the provided registration details.
     *
     * @param registerRequestDto The DTO containing user registration details.
     * @return A TokenResponseDto containing the
     * generated JWT token upon successful registration.
     */
    TokenResponseDto register(RegisterRequestDto registerRequestDto);
}
