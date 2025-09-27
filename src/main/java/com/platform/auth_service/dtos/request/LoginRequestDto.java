package com.platform.auth_service.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO for user login request.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    /**
     * The username of the user attempting to log in.
     */
    @NotBlank(message = "username cannot be blank")
    @Size(min = 4, max = 100, message = "username must be between 4 and 100 characters")
    @JsonProperty("userName")
    private String username;

    /**
     * The password of the user attempting to log in.
     */
    @NotBlank(message = "password cannot be blank")
    @Size(min = 8, max = 32, message = "password must be between 6 and 32 characters")
    @JsonProperty("password")
    private String password;
}
