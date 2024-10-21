package com.example.book.controller;

import com.example.book.borrow.repository.BorrowRepository;
import com.example.book.exception.BorrowNotFound;
import com.example.book.returns.dto.ReturnsDTO;
import com.example.book.returns.entity.ReturnsEntity;
import com.example.book.returns.mapper.ReturnsMapper;
import com.example.book.returns.service.ReturnsService;
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

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private ReturnsMapper returnsMapper;

    @PostMapping("/{borrowID}")
    public ReturnsDTO returnBorrowedBook(@PathVariable Integer borrowID) {
        borrowRepository.findById(borrowID).orElseThrow(BorrowNotFound::new);
        ReturnsEntity returnsEntity = returnsService.returnBook(borrowID);
        return returnsMapper.toReturnsDto(returnsEntity);
    }
}
