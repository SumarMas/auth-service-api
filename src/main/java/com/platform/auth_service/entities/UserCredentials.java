package com.platform.auth_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_credentials", uniqueConstraints = @UniqueConstraint(name = "uk_user_credentials_username", columnNames = "username"))
public class UserCredentials {
    /**
     * Unique identifier for the user credentials.
     */
    @Id
    @Column(name = "user_credentials_id", nullable = false, length = 36)
    private UUID id;

    /**
     * ID of the user this credentials belong to.
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

    /**
     * Indicates if the user account is enabled.
     */
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    /**
     * Timestamp when the credentials were created.
     */
    @Column(name = "created_datetime", nullable = false)
    private LocalDateTime createdDatetime;

    /**
     * ID of the user who created these credentials.
     */
    @Column(name = "created_user", nullable = false, length = 36)
    private UUID createdUser;

    /**
     * Timestamp when the credentials were last updated.
     */
    @Column(name = "last_updated_datetime", nullable = false)
    private LocalDateTime lastUpdatedDatetime;

    /**
     * ID of the user who last updated these credentials.
     */
    @Column(name = "last_updated_user", nullable = false, length = 36)
    private UUID lastUpdatedUser;

    /**
     * Sets creation and last updated timestamps before persisting.
     */
    @PrePersist
    void onCreate() {
        var now = LocalDateTime.now();
        this.createdDatetime = now;
        this.lastUpdatedDatetime = now;
    }

    /**
     * Updates the last updated timestamp before updating.
     */
    @PreUpdate
    void onUpdate() {
        this.lastUpdatedDatetime = LocalDateTime.now();
    }
}
