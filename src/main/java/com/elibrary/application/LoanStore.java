package com.elibrary.application;

import com.elibrary.domain.loan.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanStore {
    Loan save(Loan loan);

    Optional<Loan> lockById(String id);

    boolean hasActiveLoan(String userId, String bookId);

    List<Loan> activeLoans(String userId);
}
