package com.example.book.borrow.service;

import com.example.book.book.entity.BookEntity;
import com.example.book.borrow.dto.BorrowDTO;
import com.example.book.borrow.entity.BorrowEntity;
import com.example.book.user.dto.UserSessionDTO;

import java.util.List;

public interface BorrowService {
    List<BorrowDTO> getBorrowList();
    BorrowDTO addBorrow(Integer BookID);
    BorrowDTO getBorrow(Integer borrowID);
}
