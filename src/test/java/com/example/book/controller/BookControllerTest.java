package com.example.book.controller;

import com.example.book.dto.BookDTO;
import com.example.book.service.SessionService;
import com.example.book.service.impl.BookServiceIMPL;
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

import static org.mockito.Mockito.when;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookServiceIMPL bookService;

    @MockBean
    private SessionService sessionService;

    @Test
    public void testGetAllBooks() throws Exception {
        List<BookDTO> books = List.of(
                BookDTO.builder().id(1).title("Book1").author("Author1").build(),
                BookDTO.builder().id(2).title("Book2").author("Author2").build()
        );

        when(bookService.findAll()).thenReturn(books);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/book")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }
}