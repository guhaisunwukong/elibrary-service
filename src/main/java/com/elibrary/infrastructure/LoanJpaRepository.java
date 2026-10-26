package com.elibrary.infrastructure;

import com.elibrary.domain.loan.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface LoanJpaRepository extends JpaRepository<LoanEntity, String> {
    boolean existsByUserIdAndBookIdAndStatus(String userId, String bookId, LoanStatus status);

    List<LoanEntity> findByUserIdAndStatusOrderByBorrowedAtDesc(String userId, LoanStatus status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select l from LoanEntity l where l.id = :id")
    Optional<LoanEntity> lockById(@Param("id") String id);
}
