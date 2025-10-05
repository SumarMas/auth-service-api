USE auth_service;
INSERT INTO user_credentials (
    user_credentials_id,
    user_id,
    username,
    password_hash,
    enabled,
    created_user,
    last_updated_user
) VALUES (
             UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 1), -- nuevo UUID para auth-service
             UUID_TO_BIN('11111111-1111-1111-1111-111111111111', 1), -- user_id ya existente en user-service
             'admin',
             '$2a$10$evdIFDkt6MdAhLx4tpErTeUSe7XDCiis2ARWZ6l0B0JP3C/mU9VQm', -- BCrypt de 'admin123'
             1,
             UUID_TO_BIN('11111111-1111-1111-1111-111111111111', 1),
             UUID_TO_BIN('11111111-1111-1111-1111-111111111111', 1)
         );
