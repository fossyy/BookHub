package com.example.book.borrow.service;

import com.example.book.borrow.dto.BorrowDTO;

import java.util.List;

public interface BorrowService {
    List<BorrowDTO> getBorrowList();
    BorrowDTO addBorrow(Integer BookID);
    BorrowDTO getBorrow(Integer borrowID);
}
