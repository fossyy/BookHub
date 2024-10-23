package com.example.book.service.impl;

import com.example.book.dto.BookDTO;
import com.example.book.entity.BookEntity;
import com.example.book.mapper.BookMapper;
import com.example.book.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceIMPLTest {
    @InjectMocks
    private BookServiceIMPL bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll_BooksExist() {
        BookEntity bookEntity1 = BookEntity.builder().id(1).title("Book One").build();
        BookEntity bookEntity2 = BookEntity.builder().id(2).title("Book Two").build();
        List<BookEntity> bookEntities = Arrays.asList(bookEntity1, bookEntity2);

        BookDTO bookDTO1 = BookDTO.builder().id(1).title("Book One").build();
        BookDTO bookDTO2 = BookDTO.builder().id(2).title("Book Two").build();

        when(bookRepository.findAll()).thenReturn(bookEntities);
        when(bookMapper.toBookDTO(bookEntity1)).thenReturn(bookDTO1);
        when(bookMapper.toBookDTO(bookEntity2)).thenReturn(bookDTO2);

        List<BookDTO> result = bookService.findAll();

        assertEquals(2, result.size(), "There should be 2 books in the result.");
        assertEquals("Book One", result.get(0).getTitle(), "First book's title should be 'Book One'.");
        assertEquals("Book Two", result.get(1).getTitle(), "Second book's title should be 'Book Two'.");
        verify(bookRepository, times(1)).findAll();
        verify(bookMapper, times(1)).toBookDTO(bookEntity1);
        verify(bookMapper, times(1)).toBookDTO(bookEntity2);
    }

    @Test
    public void testFindAll_NoBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList());

        List<BookDTO> result = bookService.findAll();

        assertEquals(0, result.size(), "The result should be empty if no books exist.");
        verify(bookRepository, times(1)).findAll();
        verify(bookMapper, never()).toBookDTO(any());
    }

    @Test
    public void testFindById_BookExists() {
        BookEntity bookEntity = BookEntity.builder().id(1).title("Book One").build();
        when(bookRepository.findById(1)).thenReturn(Optional.of(bookEntity));

        BookEntity result = bookService.findById(1);

        assertNotNull(result, "Book should be found.");
        assertEquals(1, result.getId(), "Book ID should be 1.");
        assertEquals("Book One", result.getTitle(), "Book title should be 'Book One'.");
        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    public void testFindById_BookDoesNotExist() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        BookEntity result = bookService.findById(1);

        assertNull(result, "Book should not be found.");
        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    public void testIncrementBorrowCount() {
        when(bookRepository.incrementBorrowCount(1)).thenReturn(5);

        int result = bookService.incrementBorrowCount(1);

        assertEquals(5, result, "Borrow count should be incremented to 5.");
        verify(bookRepository, times(1)).incrementBorrowCount(1);
    }
}