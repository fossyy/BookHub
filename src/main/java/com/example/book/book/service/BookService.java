package com.example.book.book.service;

import com.example.book.book.dto.BookDTO;

import java.util.List;

public interface BookService {
    List<BookDTO> findAll();
}
