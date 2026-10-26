package com.elibrary.domain.loan;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class Loan {
    private final String id;
    private final String bookId;
    private final String userId;
    private final Instant borrowedAt;
    private final Instant dueAt;
    private Instant returnedAt;
    private LoanStatus status;

    public Loan(String id, String bookId, String userId, Instant borrowedAt, Instant dueAt,
                Instant returnedAt, LoanStatus status) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.borrowedAt = borrowedAt;
        this.dueAt = dueAt;
        this.returnedAt = returnedAt;
        this.status = status;
    }

    public static Loan open(String id, String bookId, String userId, Instant now, int loanDays) {
        return new Loan(id, bookId, userId, now, now.plus(loanDays, ChronoUnit.DAYS), null, LoanStatus.ACTIVE);
    }

    public void giveBack(String userId, Instant now) {
        if (!this.userId.equals(userId)) {
            throw new LoanOwnerMismatchException(id);
        }
        if (status != LoanStatus.ACTIVE) {
            throw new LoanAlreadyReturnedException(id);
        }
        this.returnedAt = now;
        this.status = LoanStatus.RETURNED;
    }

    public String id() { return id; }
    public String bookId() { return bookId; }
    public String userId() { return userId; }
    public Instant borrowedAt() { return borrowedAt; }
    public Instant dueAt() { return dueAt; }
    public Instant returnedAt() { return returnedAt; }
    public LoanStatus status() { return status; }
}
