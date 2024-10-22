package com.example.book.service;

import com.example.book.entity.UserEntity;

import java.util.Optional;

public interface AuthenticationService {
    Optional<String> login(String username, String password);
    Optional<UserEntity> register(String username, String password);
}
