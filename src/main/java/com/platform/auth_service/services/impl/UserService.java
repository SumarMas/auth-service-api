package com.platform.auth_service.services.impl;

import com.platform.auth_service.controllers.manageExceptions.CustomException;
import com.platform.auth_service.dtos.common.UserDto;
import com.platform.auth_service.restClients.IUserRestClient;
import com.platform.auth_service.services.IUserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing user-related operations.
 */
@Service
@AllArgsConstructor
public class UserService implements IUserService {
    /** Logger instance for logging information and errors. */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    /** REST client for interacting with the user service. */
    private final IUserRestClient userRestClient;

    /**
     * Retrieves user information by user ID.
     *
     * @param userId the unique identifier of the user
     * @return a UserDto containing user details
     * @throws CustomException if the user data cannot be fetched
     */
    @Override
    public UserDto getUserByUserId(String userId) {
        return getUser(userId);
    }

    private UserDto getUser(String userId) {
        ResponseEntity<UserDto> response = userRestClient.getUserByUserId(userId);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            LOGGER.error("Failed to fetch user data for userId '{}': HTTP {} || Body: {} ",
                    userId, response.getStatusCode(), response.getBody());
            throw new CustomException("Failed to fetch user data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
