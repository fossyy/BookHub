package com.example.book.mapper;

import com.example.book.dto.BookDTO;
import com.example.book.dto.BorrowDTO;
import com.example.book.entity.AuthorEntity;
import com.example.book.entity.BookEntity;
import com.example.book.entity.BorrowEntity;
import com.example.book.entity.UserEntity;
import com.example.book.service.ReturnsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BorrowMapperTest {

    @InjectMocks
    private BorrowMapper borrowMapper;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private ReturnsService returnsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMapToDTO() {
        UserEntity userEntity = UserEntity.builder()
                .id(1)
                .username("user")
                .build();

        BookEntity bookEntity = BookEntity.builder()
                .author(new AuthorEntity())
                .title("Book 2")
                .pages(100)
                .year(2000)
                .build();

        BorrowEntity borrowEntity = BorrowEntity.builder()
                .id(1)
                .borrowedDate(LocalDate.of(2000, 1, 1))
                .user(userEntity)
                .book(bookEntity)
                .build();

        when(bookMapper.toBookDTO(any())).thenReturn(BookDTO.builder()
                .title("Book 1")
                .build());

        BorrowDTO borrowDTO = borrowMapper.toBorrowDTO(borrowEntity);

        assertEquals(borrowEntity.getUser().getId(), borrowDTO.getUserId());
        assertEquals(borrowEntity.getId(), borrowDTO.getId());
    }
}