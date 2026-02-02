package com.library.catalog.controller;

import com.library.catalog.entity.User;
import com.library.catalog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserManagementController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/create-test-user")
    public ResponseEntity<?> createTestUser() {
        // Check if user already exists
        if (userRepository.existsByUsername("admin")) {
            // Delete existing user
            userRepository.findByUsername("admin").ifPresent(user -> userRepository.delete(user));
        }

        // Create new user with proper BCrypt password
        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setEmail("admin@library.com");
        user.setRole("ADMIN");
        user.setEnabled(true);

        userRepository.save(user);

        return ResponseEntity.ok("Test user created successfully. Username: admin, Password: password123");
    }

    @GetMapping("/list")
    public ResponseEntity<?> listUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}
