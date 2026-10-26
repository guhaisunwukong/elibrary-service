package com.elibrary.domain.loan;

public class LoanAlreadyReturnedException extends RuntimeException {
    public LoanAlreadyReturnedException(String loanId) {
        super("Loan already returned: " + loanId);
    }
}
