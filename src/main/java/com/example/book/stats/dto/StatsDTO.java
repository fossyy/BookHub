package com.example.book.stats.dto;

import com.example.book.book.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatsDTO {
    private List<BookDTO> mostBorrowedBook;
    private long notReturnedInPastWeek;
    private long overdueMoreThanAWeek;
}
