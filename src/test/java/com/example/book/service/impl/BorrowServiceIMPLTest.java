package com.example.book.service.impl;

import com.example.book.dto.BorrowDTO;
import com.example.book.dto.UserSessionDTO;
import com.example.book.entity.BookEntity;
import com.example.book.entity.BorrowEntity;
import com.example.book.entity.UserEntity;
import com.example.book.exception.BorrowOwnershipException;
import com.example.book.mapper.BorrowMapper;
import com.example.book.repository.BorrowRepository;
import com.example.book.repository.UserRepository;
import com.example.book.security.BearerTokenFilter;
import com.example.book.service.BookService;
import com.example.book.service.ReturnsService;
import com.example.book.service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowServiceIMPLTest {
    @InjectMocks
    private BorrowServiceIMPL borrowService;

    @Mock
    private BorrowRepository borrowRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SessionService sessionService;


    @Mock
    private BorrowMapper borrowMapper;

    @Mock
    private BookService bookService;

    @Mock
    private ReturnsService returnsService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContextHolder securityContextHolder;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserDetails mockPrincipal = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(mockPrincipal);
    }

    @Test
    public void testGetBorrowList() {
        UserSessionDTO session = UserSessionDTO.builder()
                .id(1)
                .username("testUser")
                .build();
        when(sessionService.getSessionData(anyString())).thenReturn(Optional.of(session));

        BorrowEntity borrowEntity1 = new BorrowEntity();
        BorrowEntity borrowEntity2 = new BorrowEntity();
        when(borrowRepository.findByUserId(1)).thenReturn(Arrays.asList(borrowEntity1, borrowEntity2));

        BorrowDTO borrowDTO1 = new BorrowDTO();
        BorrowDTO borrowDTO2 = new BorrowDTO();
        when(borrowMapper.toBorrowDTO(borrowEntity1)).thenReturn(borrowDTO1);
        when(borrowMapper.toBorrowDTO(borrowEntity2)).thenReturn(borrowDTO2);

        List<BorrowDTO> result = borrowService.getBorrowList();

        assertEquals(2, result.size(), "There should be 2 borrow records in the result.");
        verify(borrowRepository, times(1)).findByUserId(1);
        verify(borrowMapper, times(1)).toBorrowDTO(borrowEntity1);
        verify(borrowMapper, times(1)).toBorrowDTO(borrowEntity2);
    }

    @Test
    public void testAddBorrow() {
        UserSessionDTO session = UserSessionDTO.builder()
                .id(1)
                .username("testUser")
                .build();
        when(sessionService.getSessionData(anyString())).thenReturn(Optional.of(session));

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1);
        when(bookService.findById(1)).thenReturn(bookEntity);

        BorrowEntity borrowEntity = new BorrowEntity();
        when(borrowRepository.save(any(BorrowEntity.class))).thenReturn(borrowEntity);

        BorrowDTO borrowDTO = new BorrowDTO();
        when(borrowMapper.toBorrowDTO(any(BorrowEntity.class))).thenReturn(borrowDTO);

        BorrowDTO result = borrowService.addBorrow(1);

        assertNotNull(result, "The borrow record should be created.");
        verify(userRepository, times(1)).findById(1);
        verify(bookService, times(1)).findById(1);
        verify(bookService, times(1)).incrementBorrowCount(1);
        verify(borrowRepository, times(1)).save(any(BorrowEntity.class));
    }

    @Test
    public void testAddBorrow_UserNotFound() {
        UserSessionDTO session = UserSessionDTO.builder()
                .id(1)
                .username("testUser")
                .build();
        when(sessionService.getSessionData(anyString())).thenReturn(Optional.of(session));

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        BorrowDTO result = borrowService.addBorrow(1);

        assertNull(result, "Borrow record should not be created when user is not found.");
        verify(userRepository, times(1)).findById(1);
        verify(bookService, never()).findById(anyInt());
        verify(borrowRepository, never()).save(any(BorrowEntity.class));
    }

    @Test
    public void testGetBorrow_BorrowExists() {
        UserSessionDTO session = UserSessionDTO.builder()
                .id(1)
                .username("testUser")
                .build();
        when(sessionService.getSessionData(anyString())).thenReturn(Optional.of(session));

        BorrowEntity borrowEntity = new BorrowEntity();
        when(borrowRepository.findByUserIdAndId(1, 1)).thenReturn(borrowEntity);

        BorrowDTO borrowDTO = new BorrowDTO();
        when(borrowMapper.toBorrowDTO(borrowEntity)).thenReturn(borrowDTO);

        BorrowDTO result = borrowService.getBorrow(1);

        assertNotNull(result, "Borrow record should be found.");
        verify(borrowRepository, times(1)).findByUserIdAndId(1, 1);
    }

    @Test
    public void testGetBorrow_BorrowNotOwned() {
        UserSessionDTO session = UserSessionDTO.builder()
                .id(1)
                .username("testUser")
                .build();
        when(sessionService.getSessionData(anyString())).thenReturn(Optional.of(session));

        when(borrowRepository.findByUserIdAndId(1, 1)).thenReturn(null);

        assertThrows(BorrowOwnershipException.class, () -> borrowService.getBorrow(1), "Should throw BorrowOwnershipException.");
        verify(borrowRepository, times(1)).findByUserIdAndId(1, 1);
    }
}