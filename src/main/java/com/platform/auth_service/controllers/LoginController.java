package com.platform.auth_service.controllers;

import com.platform.auth_service.dtos.request.LoginRequestDto;
import com.platform.auth_service.dtos.response.TokenResponseDto;
import com.platform.auth_service.services.ILoginService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/login")
@AllArgsConstructor
public class LoginController {
    /**
     * Service for handling login operations.
     */
    private final ILoginService loginService;

    /**
     * Handles user login requests.
     *
     * @param loginRequestDto the login request data transfer object containing user credentials
     * @return a ResponseEntity containing the TokenResponseDto with the generated JWT token
     */
    @PostMapping()
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        TokenResponseDto tokenResponseDto = loginService.login(loginRequestDto);
        return ResponseEntity.ok(tokenResponseDto);
    }
}
