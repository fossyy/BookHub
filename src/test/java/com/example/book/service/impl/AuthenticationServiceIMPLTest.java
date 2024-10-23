package com.example.book.service.impl;

import com.example.book.dto.UserSessionDTO;
import com.example.book.entity.UserEntity;
import com.example.book.exception.AuthenticationException;
import com.example.book.repository.UserRepository;
import com.example.book.service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceIMPLTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private SessionService sessionService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceIMPL authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_Success() {
        UserEntity mockUser = UserEntity.builder()
                .username("testuser")
                .password("password")
                .build();

        when(userRepository.findByUsername("testuser")).thenReturn(mockUser);

        String token = authenticationService.login(mockUser);

        assertNotNull(token);
        verify(sessionService, times(1)).addSession(anyString(), any(UserSessionDTO.class));
    }

    @Test
    public void testLogin_Failure_AuthenticationException() {
        UserEntity mockUser = UserEntity.builder()
                .username("testuser")
                .password("password")
                .build();

        assertThrows(AuthenticationException.class, () -> authenticationService.login(mockUser));
    }

    @Test
    public void testRegister_Success() {
        String username = "newuser";
        String password = "password";
        when(userRepository.existsByUsername(username)).thenReturn(false);
        UserEntity mockUser = UserEntity.builder().username(username).password(password).build();
        when(userRepository.save(any(UserEntity.class))).thenReturn(mockUser);

        UserEntity registeredUser = authenticationService.register(username, password);

        assertNotNull(registeredUser);
        assertEquals(username, registeredUser.getUsername());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testRegister_Failure_UsernameConflict() {
        String username = "existinguser";
        String password = "password";
        when(userRepository.existsByUsername(any())).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                authenticationService.register(username, password));
        assertEquals("Username is already in use", exception.getReason());
    }

    @Test
    public void testGetSaltString() {
        String salt = authenticationService.getSaltString();

        assertNotNull(salt);
        assertEquals(64, salt.length());
    }
}