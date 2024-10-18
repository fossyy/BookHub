package com.example.book.borrow.repository;

import com.example.book.borrow.entity.BorrowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<BorrowEntity, Integer> {
    BorrowEntity findByUserIdAndId(Integer userId, Integer bookId);
    List<BorrowEntity> findByUserId(Integer userId);
}
