package com.example.book.controller;

import com.example.book.dto.BookDTO;
import com.example.book.dto.StatsDTO;
import com.example.book.service.SessionService;
import com.example.book.service.StatsService;
import com.example.book.service.impl.StatsServiceIMPL;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(StatsController.class)
@AutoConfigureMockMvc(addFilters = false)
public class StatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StatsServiceIMPL statsService;

    @MockBean
    private SessionService sessionService;

    @Test
    public void testGetStats() throws Exception {
        List<BookDTO> books = List.of(
                BookDTO.builder().id(1).title("Book1").author("Author1").build(),
                BookDTO.builder().id(2).title("Book2").author("Author2").build(),
                BookDTO.builder().id(3).title("Book3").author("Author3").build()
        );
        StatsDTO stats = StatsDTO.builder()
                .mostBorrowedBook(books)
                .notReturnedInPastWeek(69)
                .overdueMoreThanAWeek(420)
                .build();

        when(statsService.getStats()).thenReturn(stats);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/stats")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mostBorrowedBook[0].title").value("Book1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mostBorrowedBook[1].title").value("Book2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mostBorrowedBook[2].title").value("Book3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.notReturnedInPastWeek").value(69))
                .andExpect(MockMvcResultMatchers.jsonPath("$.overdueMoreThanAWeek").value(420));
    }
}