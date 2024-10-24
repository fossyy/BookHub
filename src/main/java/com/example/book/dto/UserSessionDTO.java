package com.example.book.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UserSessionDTO {
    private Integer id;
    private String username;
}
