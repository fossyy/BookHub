package com.example.book.borrow.entity;

import com.example.book.book.entity.BookEntity;
import com.example.book.returns.entity.ReturnsEntity;
import com.example.book.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "borrow")
public class BorrowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @OneToOne(mappedBy = "borrowingEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private ReturnsEntity returnEntity;

    private LocalDate borrowedDate;
}
