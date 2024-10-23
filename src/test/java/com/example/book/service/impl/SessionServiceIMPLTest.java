package com.example.book.service.impl;

import com.example.book.dto.UserSessionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceIMPLTest {
    @InjectMocks
    private SessionServiceIMPL sessionService;

    private final String validToken = "valid-token";
    private final String invalidToken = "invalid-token";
    private UserSessionDTO userSessionDTO;

    @BeforeEach
    public void setUp() {
        userSessionDTO = UserSessionDTO.builder()
                .id(1)
                .username("testuser")
                .build();

        sessionService.addSession(validToken, userSessionDTO);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsTokenValid_ValidToken() {
        boolean result = sessionService.isTokenValid(validToken);

        assertTrue(result, "The token should be valid.");
    }

    @Test
    public void testIsTokenValid_InvalidToken() {
        boolean result = sessionService.isTokenValid(invalidToken);

        assertFalse(result, "The token should be invalid.");
    }

    @Test
    public void testGetSessionData_ValidToken() {
        Optional<UserSessionDTO> result = sessionService.getSessionData(validToken);

        assertTrue(result.isPresent(), "Session data should be present for a valid token.");
        assertEquals(userSessionDTO, result.get(), "Session data should match the one added.");
    }

    @Test
    public void testGetSessionData_InvalidToken() {
        Optional<UserSessionDTO> result = sessionService.getSessionData(invalidToken);

        assertFalse(result.isPresent(), "Session data should be absent for an invalid token.");
    }

    @Test
    public void testAddSession() {
        String newToken = "new-token";
        UserSessionDTO newUserSession = UserSessionDTO.builder()
                .id(2)
                .username("newuser")
                .build();

        sessionService.addSession(newToken, newUserSession);

        assertTrue(sessionService.isTokenValid(newToken), "The new token should be valid.");
        Optional<UserSessionDTO> sessionData = sessionService.getSessionData(newToken);
        assertTrue(sessionData.isPresent(), "Session data should be present for the new token.");
        assertEquals(newUserSession, sessionData.get(), "Session data should match the one added.");
    }

    @Test
    public void testRemoveSession() {
        sessionService.removeSession(validToken);

        assertFalse(sessionService.isTokenValid(validToken), "The token should no longer be valid after removal.");
        Optional<UserSessionDTO> result = sessionService.getSessionData(validToken);
        assertFalse(result.isPresent(), "Session data should be absent after session removal.");
    }
}