package com.example.book.exception;

public class BorrowOwnershipException extends RuntimeException {
    public BorrowOwnershipException(String message) {
        super(message);
    }

    public BorrowOwnershipException() {
        super("The borrow record does not belong to the authenticated user");
    }
}
