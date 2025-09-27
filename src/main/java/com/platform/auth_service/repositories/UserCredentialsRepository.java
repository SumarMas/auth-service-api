package com.platform.auth_service.repositories;

import com.platform.auth_service.entities.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing UserCredentials entities.
 */
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, UUID> {
    /**
     * Finds user credentials by username if the user is enabled.
     *
     * @param userName the username to search for
     * @return an Optional containing the UserCredentials if found and enabled, otherwise empty
     */
    Optional<UserCredentials> findUserCredentialsByUsernameAndEnabledIsTrue(String userName);

    /**
     * Checks if a user with the given username exists.
     *
     * @param userName the username to check
     * @return true if a user with the given username exists, otherwise false
     */
    boolean existsByUsername(String userName);
}
