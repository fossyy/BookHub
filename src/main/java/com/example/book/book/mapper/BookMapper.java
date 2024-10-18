package com.example.book.book.mapper;

import com.example.book.book.dto.BookDTO;
import com.example.book.book.entity.BookEntity;
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
