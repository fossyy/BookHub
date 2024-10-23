package com.example.book.controller;

import com.example.book.dto.BookDTO;
import com.example.book.dto.BorrowDTO;
import com.example.book.mapper.BorrowMapper;
import com.example.book.service.BorrowService;
import com.example.book.service.SessionService;
import com.example.book.service.impl.BorrowServiceIMPL;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(BorrowController.class)
@AutoConfigureMockMvc(addFilters = false)
class BorrowControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BorrowServiceIMPL borrowService;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private BorrowMapper borrowMapper;

    @Test
    public void testGetMyBorrowings() throws Exception {
        List<BorrowDTO> borrowings = List.of(
                BorrowDTO.builder()
                        .id(1)
                        .book(BookDTO.builder()
                                .id(1)
                                .title("Book1")
                                .author("Author1")
                                .build())
                        .userId(1)
                        .build(),
                BorrowDTO.builder()
                        .id(2)
                        .book(BookDTO.builder()
                                .id(2)
                                .title("Book2")
                                .author("Author2")
                                .build())
                        .userId(1)
                        .build()
        );

        when(borrowService.getBorrowList()).thenReturn(borrowings);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/borrow")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetBorrowData() throws Exception {
        BorrowDTO borrowing = BorrowDTO.builder()
                .id(1)
                .book(BookDTO.builder()
                        .id(1)
                        .title("Book1")
                        .author("Author1")
                        .build())
                .userId(1)
                .build();

        when(borrowService.getBorrow(1)).thenReturn(borrowing);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/borrow/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void testAddBorrowData() throws Exception {
        BorrowDTO borrowing = BorrowDTO.builder()
                .id(1)
                .book(BookDTO.builder()
                        .id(1)
                        .title("Book1")
                        .author("Author1")
                        .build())
                .userId(1)
                .build();

        when(borrowService.addBorrow(1)).thenReturn(borrowing);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/borrow/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }
}