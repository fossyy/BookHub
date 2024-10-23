package com.example.book.mapper;

import com.example.book.dto.BookDTO;
import com.example.book.entity.AuthorEntity;
import com.example.book.entity.BookEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookMapperTest {

    @InjectMocks
    private BookMapper bookMapper;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMapToDTO() {
        AuthorEntity authorEntity = AuthorEntity.builder()
                .id(1)
                .name("John Doe")
                .books(List.of(
                        new BookEntity()
                ))
                .language("English")
                .build();

        BookEntity bookEntity = BookEntity.builder()
                .author(authorEntity)
                .title("Book 2")
                .pages(100)
                .year(2000)
                .build();


        BookDTO bookDTO = bookMapper.toBookDTO(bookEntity);

        assertEquals(bookEntity.getTitle(), bookDTO.getTitle());
        assertEquals(bookEntity.getPages(), bookDTO.getPages());
        assertEquals(bookEntity.getYear(), bookDTO.getYear());
    }
}