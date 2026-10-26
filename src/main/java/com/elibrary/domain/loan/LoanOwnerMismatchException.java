package com.elibrary.domain.loan;

public class LoanOwnerMismatchException extends RuntimeException {
    public LoanOwnerMismatchException(String loanId) {
        super("Loan belongs to another user: " + loanId);
    }
}
