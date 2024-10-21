package com.example.book.returns.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnsDTO {
    private Integer id;
    private Integer borrowId;
    private LocalDate returnDate;
}
