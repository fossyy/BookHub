package com.example.book.authentication.service.impl;

import com.example.book.authentication.service.AuthenticationService;
import com.example.book.authentication.service.SessionService;
import com.example.book.user.dto.UserSessionDTO;
import com.example.book.user.entity.UserEntity;
import com.example.book.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
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
    public Optional<String> login(String username, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            UserEntity userEntity = userRepository.findByUsername(username);
            String token = getSaltString();
            sessionService.addSession(token, UserSessionDTO.builder()
                    .id(userEntity.getId())
                    .username(userEntity.getUsername())
                    .build());
            return Optional.of(token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Optional<UserEntity> register(String username, String password) {
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(password)
                .build();
        return Optional.of(userRepository.save(userEntity));
    }

    // Random token generator
    protected String getSaltString() {
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
