package com.example.book.mapper;

import com.example.book.dto.ReturnsDTO;
import com.example.book.entity.BorrowEntity;
import com.example.book.entity.ReturnsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReturnsMapperTest {

    @InjectMocks
    private ReturnsMapper returnsMapper;

    @Mock
    private BorrowMapper borrowMapper;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMapToDTO() {
        ReturnsEntity returnEntity = ReturnsEntity.builder()
                .id(20)
                .returnDate(LocalDate.of(2000, 1, 1))
                .borrowingEntity(new BorrowEntity())
                .build();

        ReturnsDTO returnDTO = returnsMapper.toReturnsDto(returnEntity);

        assertEquals(returnEntity.getId(), returnDTO.getId());
        assertEquals(returnEntity.getReturnDate(), returnDTO.getReturnDate());
    }
}