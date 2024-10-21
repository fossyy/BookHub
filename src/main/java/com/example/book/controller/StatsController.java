package com.example.book.controller;

import com.example.book.stats.dto.StatsDTO;
import com.example.book.stats.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class StatsController {
    @Autowired
    private StatsService statsService;

    @GetMapping
    public StatsDTO stats() {
        return statsService.getStats();
    }
}
