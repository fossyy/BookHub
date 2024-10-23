package com.example.book.service.impl;

import com.example.book.dto.UserDTO;
import com.example.book.service.AuthenticationService;
import com.example.book.exception.AuthenticationException;
import com.example.book.service.SessionService;
import com.example.book.dto.UserSessionDTO;
import com.example.book.entity.UserEntity;
import com.example.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;

@Service
public class AuthenticationServiceIMPL implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionService sessionService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceIMPL(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String login(UserDTO user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            UserEntity userEntity = userRepository.findByUsername(user.getUsername());
            String token = getSaltString();
            sessionService.addSession(token, UserSessionDTO.builder()
                    .id(userEntity.getId())
                    .username(userEntity.getUsername())
                    .build());
            return token;
        } catch (Exception e) {
            throw new AuthenticationException();
        }
    }

    @Override
    public UserEntity register(UserDTO user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already in use");
        }
        UserEntity userEntity = UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        return userRepository.save(userEntity);
    }

    // Random token generator
    @Override
    public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 64) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
}
