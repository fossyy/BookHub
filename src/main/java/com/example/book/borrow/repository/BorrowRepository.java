package com.example.book.borrow.repository;

import com.example.book.book.entity.BookEntity;
import com.example.book.borrow.entity.BorrowEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<BorrowEntity, Integer> {
    BorrowEntity findByUserIdAndId(Integer userId, Integer bookId);
    List<BorrowEntity> findByUserId(Integer userId);

    @Query("SELECT b.book " +
            "FROM BorrowEntity b " +
            "WHERE b.borrowedDate >= :startDate " +
            "GROUP BY b.book " +
            "ORDER BY COUNT(b) DESC")
    List<BookEntity> findMostBorrowedBooksOfTheWeek(LocalDate startDate, Pageable pageable);

    @Query("SELECT COUNT(b) " +
            "FROM BorrowEntity b " +
            "WHERE b.returnEntity IS NULL " +
            "AND b.borrowedDate >= :oneWeekAgo")
    long countBooksNotReturnedInPastWeek(LocalDate oneWeekAgo);

    @Query("SELECT COUNT(b) " +
            "FROM BorrowEntity b " +
            "WHERE b.returnEntity IS NULL " +
            "AND b.borrowedDate < :oneWeekAgo")
    long countBooksOverdueMoreThanAWeek(LocalDate oneWeekAgo);
}
