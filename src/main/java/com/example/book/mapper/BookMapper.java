package com.example.book.mapper;

import com.example.book.dto.BookDTO;
import com.example.book.entity.BookEntity;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public BookDTO toBookDTO(BookEntity bookEntity) {
        return BookDTO.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .year(bookEntity.getYear())
                .pages(bookEntity.getPages())
                .author(bookEntity.getAuthor().getName())
                .language(bookEntity.getAuthor().getLanguage())
                .build();
    }
}
