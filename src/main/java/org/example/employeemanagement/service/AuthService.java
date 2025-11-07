package org.example.employeemanagement.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.employeemanagement.entity.dto.AuthResponse;
import org.example.employeemanagement.entity.dto.LoginRequest;
import org.example.employeemanagement.entity.dto.RegisterRequest;
import org.example.employeemanagement.entity.model.Role;
import org.example.employeemanagement.entity.model.UserEntity;
import org.example.employeemanagement.exception.AppException;
import org.example.employeemanagement.exception.ErrorCode;
import org.example.employeemanagement.repository.RoleRepository;
import org.example.employeemanagement.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    AuthenticationManager authenticationManager;
    CustomUserDetailsService userDetailsService;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RoleRepository roleRepository;
    JwtService jwtService;

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
        );

        var user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        var userDetails =  userDetailsService.loadUserByUsername(user.getUsername());

        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
        UserEntity user = UserEntity.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Collections.singletonList(role))
                .build();
        userRepository.save(user);

        var userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
