package com.example.book.controller;

import com.example.book.dto.UserDTO;
import com.example.book.service.AuthenticationService;
import com.example.book.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO user) {
        return ResponseEntity.ok().body(authenticationService.login(user));
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody UserDTO user) {
        return ResponseEntity.ok().body(authenticationService.register(user));
    }
}
