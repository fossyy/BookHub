package com.example.book.controller;

import com.example.book.author.dto.AuthorDTO;
import com.example.book.author.service.impl.AuthorServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    @Autowired
    private AuthorServiceIMPL authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAuthors() {
        return ResponseEntity.ok().body(authorService.findAll());
    }
}
