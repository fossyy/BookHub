package com.example.book.service.impl;

import com.example.book.dto.AuthorDTO;
import com.example.book.entity.AuthorEntity;
import com.example.book.mapper.AuthorMapper;
import com.example.book.repository.AuthorRepository;
import com.example.book.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceIMPL implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    @Override
    public List<AuthorDTO> findAll() {
        List<AuthorEntity> authorEntityList = authorRepository.findAll();
        List<AuthorDTO> authorDTOList = new ArrayList<>();
        for (AuthorEntity authorEntity : authorEntityList) {
            authorDTOList.add(authorMapper.toAuthorDTO(authorEntity));
        }
        return authorDTOList;
    }
}
