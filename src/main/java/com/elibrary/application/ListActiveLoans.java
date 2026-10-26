package com.elibrary.application;

import com.elibrary.domain.loan.Loan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListActiveLoans {
    private final LoanStore loans;

    public ListActiveLoans(LoanStore loans) {
        this.loans = loans;
    }

    @Transactional(readOnly = true)
    public List<Loan> list(String userId) {
        return loans.activeLoans(userId);
    }
}
