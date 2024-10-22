package com.example.book.service.impl;

import com.example.book.entity.BorrowEntity;
import com.example.book.repository.BorrowRepository;
import com.example.book.exception.AlreadyReturned;
import com.example.book.exception.BorrowNotFound;
import com.example.book.entity.ReturnsEntity;
import com.example.book.repository.ReturnsRepository;
import com.example.book.service.ReturnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReturnsServiceIMPL implements ReturnsService {
    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private ReturnsRepository returnsRepository;

    @Override
    public ReturnsEntity returnBook(Integer borrowId) {
        BorrowEntity borrowEntity = borrowRepository.findById(borrowId).orElseThrow(BorrowNotFound::new);

        if (returnsRepository.existsByBorrowingEntity(borrowEntity)) {
            throw new AlreadyReturned();
        }

        ReturnsEntity returnsEntity = ReturnsEntity.builder()
                .borrowingEntity(borrowEntity)
                .returnDate(LocalDate.now())
                .build();

        return returnsRepository.save(returnsEntity);
    }

    @Override
    public ReturnsEntity findReturnByBorrowId(Integer borrowId) {
        return null;
    }

    @Override
    public boolean isBookReturned(Integer borrowId) {
        BorrowEntity borrowEntity = borrowRepository.findById(borrowId).orElseThrow(BorrowNotFound::new);
        return returnsRepository.existsByBorrowingEntity(borrowEntity);
    }
}
