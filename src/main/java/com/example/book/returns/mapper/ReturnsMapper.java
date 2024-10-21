package com.example.book.returns.mapper;

import com.example.book.returns.dto.ReturnsDTO;
import com.example.book.returns.entity.ReturnsEntity;
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
