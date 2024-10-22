package com.example.book.service;

import com.example.book.entity.ReturnsEntity;

public interface ReturnsService {
    ReturnsEntity returnBook(Integer borrowId);
    ReturnsEntity findReturnByBorrowId(Integer borrowId);
    boolean isBookReturned(Integer borrowId);
}
