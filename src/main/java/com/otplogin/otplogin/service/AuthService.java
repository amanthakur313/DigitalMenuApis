package com.otplogin.otplogin.service;

import org.springframework.beans.factory.annotation.Autowired;
// ✅ Ye Imports zaroori hain (Inke bina error aayega)
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.otplogin.otplogin.dto.LoginRequest;
import com.otplogin.otplogin.dto.SignupRequest;
import com.otplogin.otplogin.model.UserEntity;
import com.otplogin.otplogin.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // ✅ MISSING LINE: Password Encoder ko yahan define karna padega
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ✅ SIGNUP (With Encryption)
    public UserEntity signup(SignupRequest request) {

        // 1. Check agar username pehle se hai
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // 2. Check agar email pehle se hai
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // 3. User Create karo (Password ko Encrypt karke)
        UserEntity user = new UserEntity(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()) // 🔒 Ab ye line chalegi
        );

        return userRepository.save(user);
    }

    // ✅ LOGIN (Secure Way)
    public UserEntity login(LoginRequest request) {

        // 1. Pehle username dhoondo
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Ab Password Match karo
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return user;
    }
}