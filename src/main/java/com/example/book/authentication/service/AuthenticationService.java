package com.example.book.authentication.service;

import com.example.book.user.dto.UserSessionDTO;
import com.example.book.user.entity.UserEntity;

import java.util.Optional;

public interface AuthenticationService {
    Optional<String> login(String username, String password);
    Optional<UserEntity> register(String username, String password);
}
