package com.example.book.controller;

import com.example.book.dto.BorrowDTO;
import com.example.book.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @GetMapping
    public ResponseEntity<List<BorrowDTO>> getMyBorrowings() {
        return ResponseEntity.ok().body(borrowService.getBorrowList());
    }

    @GetMapping("/{borrowID}")
    public ResponseEntity<BorrowDTO> getBorrowData(@PathVariable Integer borrowID) {
        return ResponseEntity.ok().body(borrowService.getBorrow(borrowID));
    }

    @PostMapping("/{bookID}")
    public ResponseEntity<BorrowDTO> addBorrowData(@PathVariable Integer bookID) {
        return ResponseEntity.ok().body(borrowService.addBorrow(bookID)) ;
    }
}
