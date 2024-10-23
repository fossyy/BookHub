package com.example.book.mapper;

import com.example.book.dto.AuthorDTO;
import com.example.book.entity.AuthorEntity;
import com.example.book.entity.BookEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorMapperTest {

    @InjectMocks
    private AuthorMapper authorMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testMapToDTO() {
        List<BookEntity> bookEntityList = List.of(
                BookEntity.builder()
                        .author(new AuthorEntity())
                        .title("Book 1")
                        .pages(100)
                        .year(2000)
                        .build(),
                BookEntity.builder()
                        .author(new AuthorEntity())
                        .title("Book 2")
                        .pages(100)
                        .year(2000)
                        .build()
        );

        AuthorEntity authorEntity = AuthorEntity.builder()
                .id(1)
                .name("John Doe")
                .books(bookEntityList)
                .language("English")
                .build();

        AuthorDTO authorDTO = authorMapper.toAuthorDTO(authorEntity);

        assertEquals(authorEntity.getId(), authorDTO.getId());
        assertEquals(authorEntity.getName(), authorDTO.getName());
        assertEquals(authorEntity.getBooks().toArray().length, 2);
    }
}