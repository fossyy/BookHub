package com.example.book.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowDTO {
    private Integer id;
    private Integer userId;
    private BookDTO book;
    private Boolean returned;
    private LocalDate borrowTime;
    private LocalDate returnTime;
}
