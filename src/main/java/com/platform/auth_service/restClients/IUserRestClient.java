package com.platform.auth_service.restClients;

import com.platform.auth_service.dtos.common.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * REST client interface for interacting with the User Service.
 */
@Service
public interface IUserRestClient {
    /**
     * Retrieves user details by user ID.
     *
     * @param userId the ID of the user to retrieve
     * @return a ResponseEntity containing the UserDto
     */
    ResponseEntity<UserDto> getUserByUserId(String userId);
}
