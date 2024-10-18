package com.example.book.book.service;

import com.example.book.book.dto.BookDTO;
import com.example.book.book.entity.BookEntity;

import java.util.List;

public interface BookService {
    List<BookDTO> findAll();
    BookEntity findById(Integer id);
    int incrementBorrowCount(Integer id);
}
