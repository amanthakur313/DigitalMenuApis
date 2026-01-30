package com.otplogin.otplogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // 1. CORS Enable (Sabse Zaroori Chrome ke liye)
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of("*")); // Mobile aur Web dono allow
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("*"));
                return config;
            }))
            
            // 2. CSRF Disable
            .csrf(csrf -> csrf.disable())
            
            // 3. Permissions
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/auth/**",    // Login/Signup ke liye
                    "/api/menu/**",    // Menu ke liye
                    "/uploads/**",        // Images in static/uploads
                    "/css/**",            // CSS files
                    "/js/**",             // JS files
                    "/images/**",         // Other images
                    "/"    // Images ke liye
                ).permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }
}