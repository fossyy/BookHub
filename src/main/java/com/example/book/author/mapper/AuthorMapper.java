package com.example.book.author.mapper;

import com.example.book.author.dto.AuthorDTO;
import com.example.book.author.entity.AuthorEntity;
import com.example.book.book.entity.BookEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthorMapper {
    public AuthorDTO toAuthorDTO(AuthorEntity authorEntity) {
        return AuthorDTO.builder()
                .id(authorEntity.getId())
                .name(authorEntity.getName())
                .country(authorEntity.getCountry())
                .language(authorEntity.getLanguage())
                .books(authorEntity.getBooks().stream()
                        .map(BookEntity::getTitle)
                        .collect(Collectors.toList()))
                .build();
    }
}
