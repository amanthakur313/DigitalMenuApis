package com.otplogin.otplogin.repository;

import com.otplogin.otplogin.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    
    // Ye line add kar lena agar nahi hai toh:
    Optional<UserEntity> findByUsername(String username);
}