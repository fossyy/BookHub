package com.example.book.returns.repository;

import com.example.book.borrow.entity.BorrowEntity;
import com.example.book.returns.entity.ReturnsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReturnsRepository extends JpaRepository<ReturnsEntity, Integer> {
    Optional<ReturnsEntity> findByBorrowingEntity(BorrowEntity borrowEntity);
    boolean existsByBorrowingEntity(BorrowEntity borrowEntity);
}
