-- ================================================
-- AUTH SERVICE SCHEMA (MySQL adjusted with safety checks)
-- ================================================
CREATE DATABASE IF NOT EXISTS auth_service;
USE auth_service;

-- =========================================
-- Tabla principal: user_credentials
-- - IDs como BINARY(16) (UUID compactado)
-- - username único
-- - campos de auditoría (created/updated)
-- =========================================
CREATE TABLE IF NOT EXISTS user_credentials
(
    user_credentials_id
    BINARY
(
    16
) NOT NULL,
    user_id BINARY
(
    16
) NOT NULL, -- referencia al user-service
    username VARCHAR
(
    100
) NOT NULL,
    password_hash VARCHAR
(
    255
) NOT NULL,
    enabled TINYINT
(
    1
) NOT NULL DEFAULT 1,
    created_datetime DATETIME
(
    6
) NOT NULL DEFAULT CURRENT_TIMESTAMP
(
    6
),
    created_user BINARY
(
    16
) NOT NULL,
    last_updated_datetime DATETIME
(
    6
) NOT NULL DEFAULT CURRENT_TIMESTAMP
(
    6
) ON UPDATE CURRENT_TIMESTAMP
(
    6
),
    last_updated_user BINARY
(
    16
) NOT NULL,
    CONSTRAINT pk_user_credentials PRIMARY KEY
(
    user_credentials_id
),
    CONSTRAINT uk_user_credentials_username UNIQUE
(
    username
),
    KEY idx_user_credentials_user_id
(
    user_id
),
    KEY idx_user_credentials_enabled
(
    enabled
)
    ) ENGINE=InnoDB ROW_FORMAT= DYNAMIC;

-- =========================================
-- Tabla de auditoría: user_credentials_audit
-- - Misma estructura + version
-- - PK compuesta (id, version)
-- =========================================
CREATE TABLE IF NOT EXISTS user_credentials_audit
(
    user_credentials_id
    BINARY
(
    16
) NOT NULL,
    user_id BINARY
(
    16
) NOT NULL,
    username VARCHAR
(
    100
) NOT NULL,
    password_hash VARCHAR
(
    255
) NOT NULL,
    enabled TINYINT
(
    1
) NOT NULL,
    created_datetime DATETIME
(
    6
) NOT NULL,
    created_user BINARY
(
    16
) NOT NULL,
    last_updated_datetime DATETIME
(
    6
) NOT NULL,
    last_updated_user BINARY
(
    16
) NOT NULL,
    version INT NOT NULL,
    CONSTRAINT pk_user_credentials_audit PRIMARY KEY
(
    user_credentials_id,
    version
),
    KEY idx_user_credentials_audit_username
(
    username
),
    KEY idx_user_credentials_audit_user_id
(
    user_id
)
    ) ENGINE=InnoDB ROW_FORMAT= DYNAMIC;

-- =========================================
-- Triggers de auditoría
-- =========================================
DELIMITER $$

-- Insert: inicializa version = 1
CREATE TRIGGER trg_user_credentials_ai
    AFTER INSERT
    ON user_credentials
    FOR EACH ROW
BEGIN
    INSERT INTO user_credentials_audit (user_credentials_id,
                                        user_id,
                                        username,
                                        password_hash,
                                        enabled,
                                        created_datetime,
                                        created_user,
                                        last_updated_datetime,
                                        last_updated_user,
                                        version)
    VALUES (NEW.user_credentials_id,
            NEW.user_id,
            NEW.username,
            NEW.password_hash,
            NEW.enabled,
            NEW.created_datetime,
            NEW.created_user,
            NEW.last_updated_datetime,
            NEW.last_updated_user,
            1);
    END$$

    -- Update: preserva created_* y versiona en audit
    CREATE TRIGGER trg_user_credentials_bu
        BEFORE UPDATE
        ON user_credentials
        FOR EACH ROW
    BEGIN
        DECLARE last_version INT DEFAULT 0;

    -- Preservar campos de creación (inmutables)
    SET NEW.created_datetime = OLD.created_datetime;
    SET NEW.created_user     = OLD.created_user;

    -- Última versión registrada
        SELECT IFNULL(MAX(a.version), 0)
        INTO last_version
        FROM user_credentials_audit a
        WHERE a.user_credentials_id = OLD.user_credentials_id;

        -- Registrar nueva versión (last_version + 1) con los valores “nuevos”
        INSERT INTO user_credentials_audit (user_credentials_id,
                                            user_id,
                                            username,
                                            password_hash,
                                            enabled,
                                            created_datetime,
                                            created_user,
                                            last_updated_datetime,
                                            last_updated_user,
                                            version)
        VALUES (OLD.user_credentials_id,
                NEW.user_id,
                NEW.username,
                NEW.password_hash,
                NEW.enabled,
                NEW.created_datetime,
                NEW.created_user,
                NEW.last_updated_datetime, -- se actualizará por ON UPDATE del main
                NEW.last_updated_user,
                last_version + 1);
        END$$

        DELIMITER ;
