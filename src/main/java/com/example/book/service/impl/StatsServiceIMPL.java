package com.example.book.service.impl;

import com.example.book.dto.BookDTO;
import com.example.book.entity.BookEntity;
import com.example.book.repository.BorrowRepository;
import com.example.book.dto.StatsDTO;
import com.example.book.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StatsServiceIMPL implements StatsService {
    @Autowired
    private BorrowRepository borrowRepository;

    @Override
    public StatsDTO getStats() {
        List<BookEntity> mostBorrowedBooks = borrowRepository.findMostBorrowedBooksOfTheWeek(LocalDate.now().minusWeeks(1), PageRequest.of(0,3));
        long countBooksNotReturnedInPastWeek = borrowRepository.countBooksNotReturnedInPastWeek(LocalDate.now().minusWeeks(1));
        long countBooksOverdueMoreThanAWeek = borrowRepository.countBooksOverdueMoreThanAWeek(LocalDate.now());

        List<BookDTO> mostBorrowedBookStats = mostBorrowedBooks.stream()
                .map(book -> BookDTO.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .year(book.getYear())
                        .pages(book.getPages())
                        .author(book.getAuthor().getName())
                        .language(book.getAuthor().getLanguage())
                        .build())
                .toList();

        return StatsDTO.builder()
                .mostBorrowedBook(mostBorrowedBookStats)
                .notReturnedInPastWeek(countBooksNotReturnedInPastWeek)
                .overdueMoreThanAWeek(countBooksOverdueMoreThanAWeek)
                .build();
    }
}
