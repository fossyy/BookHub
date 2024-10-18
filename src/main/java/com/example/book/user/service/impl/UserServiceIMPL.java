package com.example.book.user.service.impl;

import com.example.book.user.entity.UserEntity;
import com.example.book.user.repository.UserRepository;
import com.example.book.user.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceIMPL implements UserService {

    @Autowired
    private UserRepository userRepository;

    public final HashMap<String, String> sessions = new HashMap<>();

    @Override
    public Optional<String> login(String username, String password) {
        if (!userRepository.existsByUsername(username)) {
            return Optional.empty();
        }

        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity.getPassword().equals(password)) {
            String token = getSaltString();
            sessions.put(token, userEntity.getPassword());
            return Optional.of(String.valueOf(token));
        } else {
            return Optional.empty();
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

    // https://stackoverflow.com/questions/20536566/creating-a-random-string-with-a-z-and-0-9-in-java
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
