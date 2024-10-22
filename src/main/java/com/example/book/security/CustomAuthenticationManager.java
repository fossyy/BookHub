package com.example.book.security;

import com.example.book.entity.UserEntity;
import com.example.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (userRepository.existsByUsername(username)) {
            UserEntity user = userRepository.findByUsername(username);
            if (!password.equals(user.getPassword())) {
                throw new BadCredentialsException("Wrong password");
            } else {
                return new UsernamePasswordAuthenticationToken(username, password);
            }
        }
        return null;
    }
}
