package com.example.book.service.impl;

import com.example.book.dto.StatsDTO;
import com.example.book.entity.AuthorEntity;
import com.example.book.entity.BookEntity;
import com.example.book.repository.BorrowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatsServiceIMPLTest {

    @InjectMocks
    private StatsServiceIMPL statsService;

    @Mock
    private BorrowRepository borrowRepository;

    private BookEntity book1;
    private BookEntity book2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        AuthorEntity author1 = AuthorEntity.builder()
                .name("Author One")
                .language("English")
                .books(new ArrayList<>())
                .build();

        book1 = new BookEntity();
        book1.setId(1);
        book1.setTitle("Book One");
        book1.setYear(2020);
        book1.setPages(300);
        book1.setAuthor(author1);

        AuthorEntity author2 = AuthorEntity.builder()
                .name("Author Two")
                .language("English")
                .books(new ArrayList<>())
                .build();

        book2 = new BookEntity();
        book2.setId(2);
        book2.setTitle("Book Two");
        book2.setYear(2021);
        book2.setPages(250);
        book2.setAuthor(author2);
    }


    @Test
    void testGetStats_NoBooks() {
        when(borrowRepository.findMostBorrowedBooksOfTheWeek(any(), any())).thenReturn(Collections.emptyList());
        when(borrowRepository.countBooksNotReturnedInPastWeek(any())).thenReturn(0L);
        when(borrowRepository.countBooksOverdueMoreThanAWeek(any())).thenReturn(0L);

        StatsDTO stats = statsService.getStats();

        assertNotNull(stats, "StatsDTO should not be null");
        assertTrue(stats.getMostBorrowedBook().isEmpty(), "Most borrowed books should be empty");
        assertEquals(0, stats.getNotReturnedInPastWeek(), "Count of books not returned should be zero");
        assertEquals(0, stats.getOverdueMoreThanAWeek(), "Count of overdue books should be zero");

        verify(borrowRepository, times(1)).findMostBorrowedBooksOfTheWeek(any(), any());
        verify(borrowRepository, times(1)).countBooksNotReturnedInPastWeek(any());
        verify(borrowRepository, times(1)).countBooksOverdueMoreThanAWeek(any());
    }

    @Test
    void testGetStats_WithBooks() {
        when(borrowRepository.findMostBorrowedBooksOfTheWeek(any(), any()))
                .thenReturn(Arrays.asList(book1, book2));
        when(borrowRepository.countBooksNotReturnedInPastWeek(any())).thenReturn(2L);
        when(borrowRepository.countBooksOverdueMoreThanAWeek(any())).thenReturn(1L);

        StatsDTO stats = statsService.getStats();

        assertNotNull(stats, "StatsDTO should not be null");
        assertEquals(2, stats.getMostBorrowedBook().size(), "Most borrowed books count should be 2");
        assertEquals(2, stats.getNotReturnedInPastWeek(), "Count of books not returned should be 2");
        assertEquals(1, stats.getOverdueMoreThanAWeek(), "Count of overdue books should be 1");

        assertEquals("Book One", stats.getMostBorrowedBook().get(0).getTitle());
        assertEquals("Book Two", stats.getMostBorrowedBook().get(1).getTitle());

        verify(borrowRepository, times(1)).findMostBorrowedBooksOfTheWeek(any(), any());
        verify(borrowRepository, times(1)).countBooksNotReturnedInPastWeek(any());
        verify(borrowRepository, times(1)).countBooksOverdueMoreThanAWeek(any());
    }
}
