package com.example.book.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleGeneralException() {
        Exception ex = new Exception("Test message");
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleGeneralException(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("An unexpected error occurred: Test message", responseEntity.getBody());
    }

    @Test
    void handleBorrowOwnershipException() {
        BorrowOwnershipException ex = new BorrowOwnershipException("Borrow ownership exception message");
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleBorrowOwnershipException(ex);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Borrow ownership exception message", responseEntity.getBody());
    }

    @Test
    void handleAuthenticationException() {
        AuthenticationException ex = new AuthenticationException("Authentication exception message");
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleAuthenticationException(ex);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Authentication exception message", responseEntity.getBody());
    }

    @Test
    void handleAlreadyReturned() {
        AlreadyReturned ex = new AlreadyReturned("Already returned exception message");
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleAlreadyReturned(ex);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("Already returned exception message", responseEntity.getBody());
    }
}