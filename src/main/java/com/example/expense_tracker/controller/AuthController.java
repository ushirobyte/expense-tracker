package com.example.expense_tracker.controller;

import com.example.expense_tracker.model.User;
import com.example.expense_tracker.model.dto.LoginRequest;
import com.example.expense_tracker.model.dto.MeResponse;
import com.example.expense_tracker.model.dto.RegisterRequest;
import com.example.expense_tracker.model.dto.TokenResponse;
import com.example.expense_tracker.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private User user;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            authService.register(registerRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return new TokenResponse(authService.login(loginRequest));
    }

    @GetMapping("/me")
    public MeResponse me(Authentication auth) {
        return authService.me(auth.getName());
    }

}
