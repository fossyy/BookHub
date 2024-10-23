package com.example.book.service;

import com.example.book.dto.UserDTO;
import com.example.book.entity.UserEntity;

public interface AuthenticationService {
    String login(UserDTO user);
    UserEntity register(UserDTO user);
    String getSaltString();
}
