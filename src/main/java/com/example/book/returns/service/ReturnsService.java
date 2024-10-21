package com.example.book.returns.service;

import com.example.book.returns.entity.ReturnsEntity;

public interface ReturnsService {
    ReturnsEntity returnBook(Integer borrowId);
    ReturnsEntity findReturnByBorrowId(Integer borrowId);
    boolean isBookReturned(Integer borrowId);
}
