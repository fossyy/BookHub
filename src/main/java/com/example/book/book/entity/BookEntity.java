package com.example.book.book.entity;

import com.example.book.author.entity.AuthorEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private Integer year;
    private Integer pages;

    @Column(name = "borrow_count", nullable = false, columnDefinition = "int default 0")
    private Integer borrowCount = 0;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private AuthorEntity author;
}
