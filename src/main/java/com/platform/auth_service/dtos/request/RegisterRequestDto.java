package com.platform.auth_service.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for user registration request.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto extends LoginRequestDto {

    /**
     * The unique identifier of the user vinculated.
     */
    @NotNull(message = "user_id is required")
    @JsonProperty("userId")
    private UUID userId;

    /**
     * The default role assigned to the user upon registration.
     */
    @NotNull(message = "default_role is required")
    @JsonProperty("defaultRole")
    private String defaultRole;
}
