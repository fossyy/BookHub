package com.example.book.borrow.mapper;

import com.example.book.book.mapper.BookMapper;
import com.example.book.borrow.dto.BorrowDTO;
import com.example.book.borrow.entity.BorrowEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BorrowMapper {
    @Autowired
    private BookMapper bookMapper;

    public BorrowDTO toBorrowDTO(BorrowEntity borrowEntity) {
        return BorrowDTO.builder()
                .id(borrowEntity.getId())
                .userId(borrowEntity.getUser().getId())
                .book(bookMapper.toBookDTO(borrowEntity.getBook()))
                .borrowTime(borrowEntity.getBorrowedDate())
                .returnTime(borrowEntity.getBorrowedDate().plusDays(7))
                .build();
    }
}
