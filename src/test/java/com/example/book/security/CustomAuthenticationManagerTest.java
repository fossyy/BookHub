package com.example.book.security;

import com.example.book.entity.UserEntity;
import com.example.book.exception.AuthenticationException;
import com.example.book.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomAuthenticationManagerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomAuthenticationManager customAuthenticationManager;

    private UserEntity userEntity;

    @BeforeEach
    public void setup() {
        userEntity = UserEntity.builder()
                .username("username")
                .password("password")
                .build();
    }

    @Test
    public void testAuthenticate_ValidCredentials_ReturnsAuthentication() throws AuthenticationException {
        when(userRepository.existsByUsername("username")).thenReturn(true);
        when(userRepository.findByUsername("username")).thenReturn(userEntity);

        Authentication authentication = customAuthenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("username", "password"));

        assertNotNull(authentication, "Authentication should not be null");
        assertEquals("username", authentication.getName(), "Username should match");
        assertEquals("password", String.valueOf(authentication.getCredentials()), "Credentials should match");
    }

    @Test
    public void testAuthenticate_InvalidCredentials_ThrowsBadCredentialsException() {
        when(userRepository.existsByUsername("username")).thenReturn(true);
        when(userRepository.findByUsername("username")).thenReturn(userEntity);

        assertThrows(BadCredentialsException.class, () -> {
            customAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken("username", "wrongpassword"));
        }, "Expected BadCredentialsException");
    }


    @Test
    public void testAuthenticate_UserDoesNotExist_ReturnsNull() throws AuthenticationException {
        when(userRepository.existsByUsername("username")).thenReturn(false);

        Authentication authentication = customAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken("username", "password"));

        assertNull(authentication);
    }
}
