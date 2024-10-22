package com.example.book.service.impl;

import com.example.book.dto.BookDTO;
import com.example.book.entity.BookEntity;
import com.example.book.mapper.BookMapper;
import com.example.book.repository.BookRepository;
import com.example.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceIMPL implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<BookDTO> findAll() {
        List<BookEntity> listBookEntity = bookRepository.findAll();
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (BookEntity bookEntity : listBookEntity) {
            bookDTOList.add(bookMapper.toBookDTO(bookEntity));
        }
        return bookDTOList;
    }

    @Override
    public BookEntity findById(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public int incrementBorrowCount(Integer id) {
        return bookRepository.incrementBorrowCount(id);
    }
}
