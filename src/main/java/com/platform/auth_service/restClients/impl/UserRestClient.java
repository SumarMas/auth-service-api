package com.platform.auth_service.restClients.impl;

import com.platform.auth_service.dtos.common.UserDto;
import com.platform.auth_service.restClients.IUserRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserRestClient implements IUserRestClient {

    /** RestTemplate instance for making HTTP requests. */
    private final RestTemplate restTemplate;

    /** Base URL for the user service. */
    private final String rootUrl;

    /**
     * Constructs a UserRestClient with the specified RestTemplate and root URL.
     *
     * @param restTemplateParam the RestTemplate instance for making HTTP requests
     * @param rootUrlParam      the base URL for the user service, injected from application properties
     */
    public UserRestClient(RestTemplate restTemplateParam, @Value("${pool.user.url}") String rootUrlParam) {
        this.rootUrl = rootUrlParam;
        this.restTemplate = restTemplateParam;
    }

    /**
     * Retrieves user information by user ID.
     *
     * @param userId the unique identifier of the user
     * @return a ResponseEntity containing the UserDto with user details
     */
    @Override
    public ResponseEntity<UserDto> getUserByUserId(String userId) {
        return null;
    }
}
