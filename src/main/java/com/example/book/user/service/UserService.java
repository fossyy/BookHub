package com.example.book.user.service;

import com.example.book.user.dto.UserSessionDTO;
import com.example.book.user.entity.UserEntity;
import org.apache.catalina.User;

import java.util.Optional;

public interface UserService {
    boolean isAuthenticated (String token);
    UserSessionDTO getSessionData (String token);
    Optional<String> login(String username, String password);
    Optional<UserEntity> register(String username, String password);
}
