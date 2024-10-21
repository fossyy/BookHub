package com.example.book.exception;

public class BorrowNotFound extends RuntimeException {
    public BorrowNotFound(String message) {
        super(message);
    }
    public BorrowNotFound() {
        super("Return not found for this borrowing");
    }
}
