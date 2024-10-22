package com.example.book.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class AuthorDTO {
    private Integer id;
    private String name;
    private String country;
    private String language;
    private List<String> books;
}
