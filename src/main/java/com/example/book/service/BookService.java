package com.example.book.service;

import com.example.book.dto.BookDTO;
import com.example.book.entity.BookEntity;

import java.util.List;

public interface BookService {
    List<BookDTO> findAll();
    BookEntity findById(Integer id);
    int incrementBorrowCount(Integer id);
}
