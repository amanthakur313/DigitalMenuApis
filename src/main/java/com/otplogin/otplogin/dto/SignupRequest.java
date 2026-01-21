package com.otplogin.otplogin.dto;

import jakarta.validation.constraints.Size;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



public class SignupRequest {

	 @NotBlank(message = "Username required")
    private String username;

    public String getUsername() {
		return username;
	}

	private void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	private void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	private void setPassword(String password) {
		this.password = password;
	}

	@Email(message = "Invalid email format")
    @NotBlank(message = "Email required")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    // getters & setters
}
