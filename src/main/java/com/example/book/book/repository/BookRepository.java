package com.example.book.book.repository;

import com.example.book.book.entity.BookEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE BookEntity b SET b.borrowCount = b.borrowCount + 1 WHERE b.id = :bookId")
    int incrementBorrowCount(Integer bookId);
}
