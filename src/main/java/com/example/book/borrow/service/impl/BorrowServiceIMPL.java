package com.example.book.borrow.service.impl;

import com.example.book.book.entity.BookEntity;
import com.example.book.book.service.BookService;
import com.example.book.borrow.dto.BorrowDTO;
import com.example.book.borrow.entity.BorrowEntity;
import com.example.book.borrow.mapper.BorrowMapper;
import com.example.book.borrow.repository.BorrowRepository;
import com.example.book.borrow.service.BorrowService;
import com.example.book.exception.BorrowOwnershipException;
import com.example.book.user.dto.UserSessionDTO;
import com.example.book.user.entity.UserEntity;
import com.example.book.user.repository.UserRepository;
import com.example.book.user.service.impl.UserServiceIMPL;
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
    private UserServiceIMPL userService;
    
    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private BookService bookService;

    @Override
    public List<BorrowDTO> getBorrowList() {
        UserSessionDTO session = userService.getSessionData(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        List<BorrowEntity> borrowEntityList = borrowRepository.findByUserId(session.getId());
        List<BorrowDTO> borrowDTOList = new ArrayList<>();
        borrowEntityList.stream().map(borrowMapper::toBorrowDTO).forEach(borrowDTOList::add);
        return borrowDTOList;
    }

    @Override
    public BorrowDTO addBorrow(Integer BookID) {
        UserSessionDTO session = userService.getSessionData(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        BorrowEntity borrowData = new BorrowEntity();

        Optional<UserEntity> userEntity = userRepository.findById(session.getId());
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
        UserSessionDTO session = userService.getSessionData(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        BorrowEntity borrowEntity = borrowRepository.findByUserIdAndId(session.getId(), borrowID);
        if (borrowEntity == null) {
            throw new BorrowOwnershipException();
        }
        return borrowMapper.toBorrowDTO(borrowEntity);
    }
}
