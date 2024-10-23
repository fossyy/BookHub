package com.example.book.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlreadyReturnedTest {
    @Test
    public void testAlreadyReturned() {
        try {
            throw new AlreadyReturned();
        } catch (AlreadyReturned e) {
            assertEquals("Already returned", e.getMessage());
        }
    }
}