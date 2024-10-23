package com.example.book.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationExceptionTest {
    @Test
    public void testAuthenticationException() {
        try {
            throw new AuthenticationException();
        } catch (AuthenticationException e) {
            assertEquals("Username or password is incorrect", e.getMessage());
        }
    }
}