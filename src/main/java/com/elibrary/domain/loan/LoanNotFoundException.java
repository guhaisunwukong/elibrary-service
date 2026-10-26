package com.elibrary.domain.loan;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(String loanId) {
        super("Loan not found: " + loanId);
    }
}
