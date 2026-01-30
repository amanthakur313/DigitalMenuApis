// package com.otplogin.otplogin.config;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.web.filter.CorsFilter;
//
// import java.util.List;
//
// @Configuration
// public class SecurityConfig {
//
//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//         http
//             // 1. CORS Enable (Sabse Zaroori Chrome ke liye)
//             .cors(cors -> cors.configurationSource(request -> {
//                 CorsConfiguration config = new CorsConfiguration();
//                 config.setAllowedOrigins(List.of("*")); // Mobile aur Web dono allow
//                 config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                 config.setAllowedHeaders(List.of("*"));
//                 return config;
//             }))
//            
//             // 2. CSRF Disable
//             .csrf(csrf -> csrf.disable())
//            
//             // 3. Permissions
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers(
//                     "/api/auth/**",    // Login/Signup ke liye
//                     "/api/menu/**",    // Menu ke liye
//                     "/uploads/**",        // Images in static/uploads
//                     "/css/**",            // CSS files
//                     "/js/**",             // JS files
//                     "/images/**",         // Other images
//                     "/"    // Images ke liye
//                 ).permitAll()
//                 .requestMatchers(HttpMethod.POST, "/api/menu/place").permitAll()
//                 .anyRequest().authenticated()
//             );
//
//         return http.build();
//     }
// }


package com.otplogin.otplogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. CSRF Disable (POST requests ke liye zaroori)
            .csrf(csrf -> csrf.disable())

            // 2. CORS Enable (Niche wala bean use karega)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // 3. Permissions Setup
            .authorizeHttpRequests(auth -> auth
                // A. Sabse pehle OPTIONS (Pre-flight) requests allow karein
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                
                // B. Login/Signup/Images Public rakhein
                .requestMatchers("/api/auth/**", "/uploads/**", "/images/**", "/css/**", "/js/**").permitAll()

                // C. Menu ki GET aur POST dono requests explicitly allow karein
                .requestMatchers(HttpMethod.GET, "/api/menu/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/menu/place").permitAll() // <--- Yeh line 403 fix karegi
                
                // D. Testing ke liye agar sab kholna ho (Optional)
                .requestMatchers("/api/**").permitAll()

                // E. Baaki sab secured
                .anyRequest().authenticated()
            )

            // 4. Session Policy (Stateless rakhein taaki login page na maange)
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    // 5. Global CORS Configuration Bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Mobile aur Web se access ke liye "*" lagaya hai
        configuration.setAllowedOrigins(List.of("*")); 
        
        // Saare Methods Allow kiye
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Saare Headers Allow kiye
        configuration.setAllowedHeaders(List.of("*"));
        
        // Credentials false rakhein jab Origin "*" ho
        configuration.setAllowCredentials(false); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
