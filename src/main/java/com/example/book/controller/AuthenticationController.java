package com.example.book.controller;

import com.example.book.user.entity.UserEntity;
import com.example.book.user.service.impl.UserServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private UserServiceIMPL userService;

    @GetMapping
    public ResponseEntity<Optional<String>> login(@RequestBody UserEntity user) {
        return ResponseEntity.ok().body(userService.login(user.getUsername(), user.getPassword()));
    }

    @PostMapping
    public ResponseEntity<Optional<UserEntity>> register(@RequestBody UserEntity user) {
        return ResponseEntity.ok().body(userService.register(user.getUsername(), user.getPassword()));
    }

}
