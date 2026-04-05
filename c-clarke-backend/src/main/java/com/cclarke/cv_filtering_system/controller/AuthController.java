package com.cclarke.cv_filtering_system.controller;

import com.cclarke.cv_filtering_system.model.User;
import com.cclarke.cv_filtering_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already taken!");
        }
        user.setRole("CANDIDATE");
        userRepository.save(user);
        return ResponseEntity.ok("Account created successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginData) {
        User user = userRepository.findByUsername(loginData.getUsername());
        if (user != null && user.getPassword().equals(loginData.getPassword())) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}