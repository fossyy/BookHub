package com.example.book.service.impl;

import com.example.book.dto.AuthorDTO;
import com.example.book.entity.AuthorEntity;
import com.example.book.mapper.AuthorMapper;
import com.example.book.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthorServiceIMPLTest {
    @InjectMocks
    private AuthorServiceIMPL authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll_AuthorsExist() {
        AuthorEntity authorEntity1 = AuthorEntity.builder().id(1).name("Author One").build();
        AuthorEntity authorEntity2 = AuthorEntity.builder().id(2).name("Author Two").build();
        List<AuthorEntity> authorEntities = Arrays.asList(authorEntity1, authorEntity2);

        AuthorDTO authorDTO1 = AuthorDTO.builder().id(1).name("Author One").build();
        AuthorDTO authorDTO2 = AuthorDTO.builder().id(2).name("Author Two").build();

        when(authorRepository.findAll()).thenReturn(authorEntities);
        when(authorMapper.toAuthorDTO(authorEntity1)).thenReturn(authorDTO1);
        when(authorMapper.toAuthorDTO(authorEntity2)).thenReturn(authorDTO2);

        List<AuthorDTO> result = authorService.findAll();

        assertEquals(2, result.size(), "There should be 2 authors in the result.");
        assertEquals("Author One", result.get(0).getName(), "First author's name should be 'Author One'.");
        assertEquals("Author Two", result.get(1).getName(), "Second author's name should be 'Author Two'.");
        verify(authorRepository, times(1)).findAll();
        verify(authorMapper, times(1)).toAuthorDTO(authorEntity1);
        verify(authorMapper, times(1)).toAuthorDTO(authorEntity2);
    }

    @Test
    public void testFindAll_NoAuthors() {
        when(authorRepository.findAll()).thenReturn(Arrays.asList());

        List<AuthorDTO> result = authorService.findAll();

        assertEquals(0, result.size(), "The result should be empty if no authors exist.");
        verify(authorRepository, times(1)).findAll();
        verify(authorMapper, never()).toAuthorDTO(any());
    }
}