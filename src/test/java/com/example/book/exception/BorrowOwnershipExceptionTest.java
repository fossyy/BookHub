package com.example.book.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BorrowOwnershipExceptionTest {
    @Test
    public void testBorrowOwnershipException() {
        try {
            throw new BorrowOwnershipException();
        } catch (BorrowOwnershipException e) {
            assertEquals("The borrow record does not belong to the authenticated user", e.getMessage());
        }
    }
}