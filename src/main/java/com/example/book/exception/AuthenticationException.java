package com.example.book.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException() {
        super("Username or password is incorrect");
    }
}