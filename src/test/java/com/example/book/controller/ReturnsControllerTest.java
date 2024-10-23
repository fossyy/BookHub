package com.example.book.controller;

import com.example.book.dto.ReturnsDTO;
import com.example.book.mapper.ReturnsMapper;
import com.example.book.repository.BorrowRepository;
import com.example.book.service.SessionService;
import com.example.book.service.impl.ReturnsServiceIMPL;
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

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@WebMvcTest(ReturnsController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReturnsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReturnsServiceIMPL returnService;

    @MockBean
    private SessionService sessionService;

    @Test
    public void testAddReturnData() throws Exception {
        ReturnsDTO returnData = ReturnsDTO.builder().id(1).borrowId(1).returnDate(LocalDate.now()).build();

        when(returnService.returnBook(1)).thenReturn(returnData);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/return/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }
}