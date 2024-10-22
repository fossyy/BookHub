package com.example.book.mapper;

import com.example.book.dto.ReturnsDTO;
import com.example.book.entity.ReturnsEntity;
import org.springframework.stereotype.Component;

@Component
public class ReturnsMapper {
    public ReturnsDTO toReturnsDto(ReturnsEntity returnsEntity) {
        return ReturnsDTO.builder()
                .id(returnsEntity.getId())
                .borrowId(returnsEntity.getBorrowingEntity().getId())
                .returnDate(returnsEntity.getReturnDate())
                .build();
    }
}
