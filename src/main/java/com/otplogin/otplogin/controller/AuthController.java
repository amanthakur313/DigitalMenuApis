package com.otplogin.otplogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.otplogin.otplogin.dto.LoginRequest;
import com.otplogin.otplogin.dto.SignupRequest;
import com.otplogin.otplogin.model.UserEntity;
import com.otplogin.otplogin.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ✅ SIGNUP API
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        try {
            UserEntity newUser = authService.signup(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (RuntimeException e) {
            // Agar Username/Email exist karta hai, toh Error message bhejo
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ✅ LOGIN API
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            UserEntity user = authService.login(request);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            // Agar Password galat hai ya User nahi mila
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}