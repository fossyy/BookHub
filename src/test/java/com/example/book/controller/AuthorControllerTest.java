package com.example.book.controller;

import com.example.book.dto.AuthorDTO;
import com.example.book.service.SessionService;
import com.example.book.service.impl.AuthorServiceIMPL;
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

@WebMvcTest(AuthorController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorServiceIMPL authorService;

    @MockBean
    private SessionService sessionService;

    @Test
    public void testGetAllAuthors() throws Exception {
        List<AuthorDTO> authors = List.of(
                AuthorDTO.builder().id(1).name("Author1").build(),
                AuthorDTO.builder().id(2).name("Author2").build()
        );

        when(authorService.findAll()).thenReturn(authors);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/author")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }
}