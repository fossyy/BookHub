package com.example.book.exception;

public class BorrowNotFound extends RuntimeException {
    public BorrowNotFound() {
        super("Return not found for this borrowing");
    }
}
