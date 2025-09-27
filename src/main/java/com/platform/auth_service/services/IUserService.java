package com.platform.auth_service.services;

import com.platform.auth_service.dtos.common.UserDto;
import org.springframework.stereotype.Service;

/**
 * Service interface for user-related operations.
 */
@Service
public interface IUserService {
    /**
     * Retrieves user details by user ID.
     *
     * @param userId the ID of the user to retrieve
     * @return a UserDto containing user details
     */
    UserDto getUserByUserId(String userId);
}
