package com.example.book.service.impl;

import com.example.book.dto.ReturnsDTO;
import com.example.book.entity.BorrowEntity;
import com.example.book.entity.ReturnsEntity;
import com.example.book.exception.AlreadyReturned;
import com.example.book.exception.BorrowNotFound;
import com.example.book.mapper.ReturnsMapper;
import com.example.book.repository.BorrowRepository;
import com.example.book.repository.ReturnsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReturnsServiceIMPLTest {
    @InjectMocks
    private ReturnsServiceIMPL returnsService;

    @Mock
    private BorrowRepository borrowRepository;

    @Mock
    private ReturnsRepository returnsRepository;

    @Mock
    private ReturnsMapper returnsMapper;

    @Mock
    private BorrowEntity borrowEntity;

    @Mock
    private ReturnsEntity returnsEntity;

    @Mock
    private ReturnsDTO returnsDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReturnBook_BorrowNotFound() {
        when(borrowRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(BorrowNotFound.class, () -> returnsService.returnBook(1));
        verify(borrowRepository, times(1)).findById(1);
    }

    @Test
    void testReturnBook_AlreadyReturned() {
        when(borrowRepository.findById(1)).thenReturn(Optional.of(borrowEntity));
        when(returnsRepository.existsByBorrowingEntity(borrowEntity)).thenReturn(true);

        assertThrows(AlreadyReturned.class, () -> returnsService.returnBook(1));
        verify(borrowRepository, times(1)).findById(1);
        verify(returnsRepository, times(1)).existsByBorrowingEntity(borrowEntity);
    }

    @Test
    void testReturnBook_Success() {
        when(borrowRepository.findById(1)).thenReturn(Optional.of(borrowEntity));
        when(returnsRepository.existsByBorrowingEntity(borrowEntity)).thenReturn(false);
        when(returnsRepository.save(any(ReturnsEntity.class))).thenReturn(returnsEntity);
        when(returnsMapper.toReturnsDto(returnsEntity)).thenReturn(returnsDTO);

        ReturnsDTO result = returnsService.returnBook(1);

        assertNotNull(result, "Returned DTO should not be null");
        verify(borrowRepository, times(1)).findById(1);
        verify(returnsRepository, times(1)).existsByBorrowingEntity(borrowEntity);
        verify(returnsRepository, times(1)).save(any(ReturnsEntity.class));
        verify(returnsMapper, times(1)).toReturnsDto(returnsEntity);
    }

    @Test
    void testIsBookReturned_BorrowNotFound() {
        when(borrowRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(BorrowNotFound.class, () -> returnsService.isBookReturned(1));
        verify(borrowRepository, times(1)).findById(1);
    }

    @Test
    void testIsBookReturned_Success() {
        when(borrowRepository.findById(1)).thenReturn(Optional.of(borrowEntity));
        when(returnsRepository.existsByBorrowingEntity(borrowEntity)).thenReturn(true);

        boolean result = returnsService.isBookReturned(1);

        assertTrue(result, "The book should be marked as returned.");
        verify(borrowRepository, times(1)).findById(1);
        verify(returnsRepository, times(1)).existsByBorrowingEntity(borrowEntity);
    }
}