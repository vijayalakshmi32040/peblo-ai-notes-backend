package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // Signup API
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {

        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body("Email already exists");
        }

        userRepository.save(user);

        return ResponseEntity.ok("User Registered Successfully");
    }

    // Login API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        User existingUser = userRepository
                .findByEmail(user.getEmail())
                .orElse(null);

        if(existingUser == null) {
            return ResponseEntity.badRequest()
                    .body("User not found");
        }

        if(!existingUser.getPassword()
                .equals(user.getPassword())) {

            return ResponseEntity.badRequest()
                    .body("Invalid Password");
        }

        return ResponseEntity.ok("Login Successful");
    }
}