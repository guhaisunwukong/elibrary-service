package com.elibrary.infrastructure;

import com.elibrary.application.LoanStore;
import com.elibrary.domain.loan.Loan;
import com.elibrary.domain.loan.LoanStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaLoanStore implements LoanStore {
    private final LoanJpaRepository loans;

    public JpaLoanStore(LoanJpaRepository loans) {
        this.loans = loans;
    }

    @Override
    public Loan save(Loan loan) {
        return toDomain(loans.save(toEntity(loan)));
    }

    @Override
    public Optional<Loan> lockById(String id) {
        return loans.lockById(id).map(JpaLoanStore::toDomain);
    }

    @Override
    public boolean hasActiveLoan(String userId, String bookId) {
        return loans.existsByUserIdAndBookIdAndStatus(userId, bookId, LoanStatus.ACTIVE);
    }

    @Override
    public List<Loan> activeLoans(String userId) {
        return loans.findByUserIdAndStatusOrderByBorrowedAtDesc(userId, LoanStatus.ACTIVE)
                .stream()
                .map(JpaLoanStore::toDomain)
                .collect(Collectors.toList());
    }

    static Loan toDomain(LoanEntity entity) {
        return new Loan(entity.getId(), entity.getBookId(), entity.getUserId(), entity.getBorrowedAt(),
                entity.getDueAt(), entity.getReturnedAt(), entity.getStatus());
    }

    static LoanEntity toEntity(Loan loan) {
        LoanEntity entity = new LoanEntity();
        entity.setId(loan.id());
        entity.setBookId(loan.bookId());
        entity.setUserId(loan.userId());
        entity.setBorrowedAt(loan.borrowedAt());
        entity.setDueAt(loan.dueAt());
        entity.setReturnedAt(loan.returnedAt());
        entity.setStatus(loan.status());
        return entity;
    }
}
