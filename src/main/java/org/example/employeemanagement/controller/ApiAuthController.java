package org.example.employeemanagement.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.employeemanagement.entity.dto.ApiResponse;
import org.example.employeemanagement.entity.dto.AuthResponse;
import org.example.employeemanagement.entity.dto.LoginRequest;
import org.example.employeemanagement.entity.dto.RegisterRequest;
import org.example.employeemanagement.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/auth")
public class ApiAuthController {
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @RequestBody @Valid LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success(authResponse, "Đăng nhập thành công"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @RequestBody @Valid RegisterRequest registerRequest) {
        AuthResponse authResponse = authService.register(registerRequest);
        return ResponseEntity.ok(ApiResponse.success(authResponse, "Đăng ký thành công"));
    }
}
