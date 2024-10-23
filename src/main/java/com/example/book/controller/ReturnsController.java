package com.example.book.controller;

import com.example.book.dto.ReturnsDTO;
import com.example.book.service.ReturnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/return")
public class ReturnsController {
    @Autowired
    private ReturnsService returnsService;

    @PostMapping("/{borrowID}")
    public ReturnsDTO returnBorrowedBook(@PathVariable Integer borrowID) {
        return returnsService.returnBook(borrowID);
    }
}
