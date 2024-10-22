package com.example.book.service;

import com.example.book.dto.BorrowDTO;

import java.util.List;

public interface BorrowService {
    List<BorrowDTO> getBorrowList();
    BorrowDTO addBorrow(Integer BookID);
    BorrowDTO getBorrow(Integer borrowID);
}
