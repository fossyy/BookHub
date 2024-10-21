package com.example.book.exception;

public class AlreadyReturned extends RuntimeException {
    public AlreadyReturned(String message) {
        super(message);
    }
    public AlreadyReturned() {
        super("Already returned");
    }
}
