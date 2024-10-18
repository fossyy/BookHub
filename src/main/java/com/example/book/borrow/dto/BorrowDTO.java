package com.example.book.borrow.dto;

import com.example.book.book.dto.BookDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Setter
@Getter
public class BorrowDTO {
    private Integer id;
    private Integer userId;
    private BookDTO book;
    private LocalDate borrowTime;
    private LocalDate returnTime;
}
