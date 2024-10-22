package com.example.book.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class BookDTO {
    private Integer id;
    private String title;
    private Integer year;
    private Integer pages;
    private String author;
    private String language;
}
