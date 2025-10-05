package com.platform.auth_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Entity representing user credentials for authentication.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "user_credentials", uniqueConstraints = @UniqueConstraint(name = "uk_user_credentials_username", columnNames = "username"))
public class UserCredentials extends AuditEntity {
    /**
     * Unique identifier for the user credentials.
     */
    @Id
    @Column(name = "user_credentials_id", nullable = false, length = 36)
    private UUID id;

    /**
     * ID of the user these credentials belong to.
     */
    @Column(name = "user_id", nullable = false, length = 36)
    private UUID userId; // mismo ID que usará user-service

    /**
     * Username for login.
     */
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    /**
     * Hashed password for security.
     */
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
}
