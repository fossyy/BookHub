package com.example.book.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BorrowNotFoundTest {
    @Test
    public void testBorrowNotFound() {
        try {
            throw new BorrowNotFound();
        } catch (BorrowNotFound e) {
            assertEquals("Return not found for this borrowing", e.getMessage());
        }
    }
}