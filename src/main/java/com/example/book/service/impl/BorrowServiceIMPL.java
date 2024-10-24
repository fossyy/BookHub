package com.example.book.service.impl;

import com.example.book.entity.BookEntity;
import com.example.book.service.BookService;
import com.example.book.dto.BorrowDTO;
import com.example.book.entity.BorrowEntity;
import com.example.book.mapper.BorrowMapper;
import com.example.book.repository.BorrowRepository;
import com.example.book.service.BorrowService;
import com.example.book.exception.BorrowOwnershipException;
import com.example.book.service.ReturnsService;
import com.example.book.service.SessionService;
import com.example.book.dto.UserSessionDTO;
import com.example.book.entity.UserEntity;
import com.example.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowServiceIMPL implements BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SessionService sessionService;
    
    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReturnsService returnsService;

    @Override
    public List<BorrowDTO> getBorrowList() {
        Optional<UserSessionDTO> session = sessionService.getSessionData(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        List<BorrowEntity> borrowEntityList = borrowRepository.findByUserId(session.get().getId());
        List<BorrowDTO> borrowDTOList = new ArrayList<>();
        borrowEntityList.stream().map(borrowMapper::toBorrowDTO).forEach(borrowDTOList::add);
        return borrowDTOList;
    }

    @Override
    public BorrowDTO addBorrow(Integer BookID) {
        Optional<UserSessionDTO> session = sessionService.getSessionData(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        BorrowEntity borrowData = new BorrowEntity();

        Optional<UserEntity> userEntity = userRepository.findById(session.get().getId());
        if (userEntity.isEmpty()) {
            return null;
        }

        BookEntity bookEntity = bookService.findById(BookID);
        borrowData.setBook(bookEntity);
        borrowData.setUser(userEntity.get());
        borrowData.setBorrowedDate(LocalDate.now());

        bookService.incrementBorrowCount(BookID);

        return borrowMapper.toBorrowDTO(borrowRepository.save(borrowData));
    }

    @Override
    public BorrowDTO getBorrow(Integer borrowID) {
        Optional<UserSessionDTO> session = sessionService.getSessionData(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        BorrowEntity borrowEntity = borrowRepository.findByUserIdAndId(session.get().getId(), borrowID);
        if (borrowEntity == null) {
            throw new BorrowOwnershipException();
        }

        return borrowMapper.toBorrowDTO(borrowEntity);
    }
}
