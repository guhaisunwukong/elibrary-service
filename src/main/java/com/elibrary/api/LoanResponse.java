package com.elibrary.api;

import com.elibrary.domain.loan.Loan;
import com.elibrary.domain.loan.LoanStatus;

import java.time.Instant;

public class LoanResponse {
    private String id;
    private String bookId;
    private String userId;
    private Instant borrowedAt;
    private Instant dueAt;
    private Instant returnedAt;
    private LoanStatus status;

    public static LoanResponse of(Loan loan) {
        LoanResponse response = new LoanResponse();
        response.id = loan.id();
        response.bookId = loan.bookId();
        response.userId = loan.userId();
        response.borrowedAt = loan.borrowedAt();
        response.dueAt = loan.dueAt();
        response.returnedAt = loan.returnedAt();
        response.status = loan.status();
        return response;
    }

    public String getId() { return id; }
    public String getBookId() { return bookId; }
    public String getUserId() { return userId; }
    public Instant getBorrowedAt() { return borrowedAt; }
    public Instant getDueAt() { return dueAt; }
    public Instant getReturnedAt() { return returnedAt; }
    public LoanStatus getStatus() { return status; }
}
