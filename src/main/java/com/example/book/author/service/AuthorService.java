package com.example.book.author.service;

import com.example.book.author.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAll();
}
