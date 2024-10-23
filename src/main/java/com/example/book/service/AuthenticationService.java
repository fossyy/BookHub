package com.example.book.service;

import com.example.book.entity.UserEntity;

import java.util.Optional;

public interface AuthenticationService {
    String login(UserEntity user);
    UserEntity register(String username, String password);
    String getSaltString();
}
