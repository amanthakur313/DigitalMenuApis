package com.otplogin.otplogin.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    // 🔹 Getter
    public String getUsername() {
        return username;
    }

    // 🔹 Setter
    public void setUsername(String username) {
        this.username = username;
    }

    // 🔹 Getter
    public String getPassword() {
        return password;
    }

    // 🔹 Setter
    public void setPassword(String password) {
        this.password = password;
    }
}
