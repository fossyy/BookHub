package com.example.book.service;

import com.example.book.dto.ReturnsDTO;

public interface ReturnsService {
    ReturnsDTO returnBook(Integer borrowId);
    boolean isBookReturned(Integer borrowId);
}
