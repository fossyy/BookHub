package com.example.book.controller;

import com.example.book.book.dto.BookDTO;
import com.example.book.book.entity.BookEntity;
import com.example.book.book.repository.BookRepository;
import com.example.book.book.service.BookService;
import com.example.book.borrow.dto.BorrowDTO;
import com.example.book.borrow.entity.BorrowEntity;
import com.example.book.borrow.mapper.BorrowMapper;
import com.example.book.borrow.service.BorrowService;
import com.example.book.user.dto.UserSessionDTO;
import com.example.book.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @Autowired
    private UserService userService;

    @Autowired
    private BorrowMapper borrowMapper;

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
