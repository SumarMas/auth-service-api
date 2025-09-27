package com.platform.auth_service.controllers;

import com.platform.auth_service.dtos.request.RegisterRequestDto;
import com.platform.auth_service.dtos.response.TokenResponseDto;
import com.platform.auth_service.services.IRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/register")
@RequiredArgsConstructor
public class RegisterController {
    /** Service for handling user registration operations. */
    private final IRegisterService registerService;

    /**
     * Handles user registration requests.
     *
     * @param registerRequestDto the registration request data transfer object containing user details
     * @return a ResponseEntity containing the TokenResponseDto with the generated JWT token
     */
    @PostMapping()
    public ResponseEntity<TokenResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        TokenResponseDto tokenResponseDto = registerService.register(registerRequestDto);
        return ResponseEntity.ok(tokenResponseDto);
    }
}
