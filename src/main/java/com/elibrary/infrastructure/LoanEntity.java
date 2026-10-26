package com.elibrary.infrastructure;

import com.elibrary.domain.loan.LoanStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "loans")
public class LoanEntity {
    @Id
    private String id;
    private String bookId;
    private String userId;
    private Instant borrowedAt;
    private Instant dueAt;
    private Instant returnedAt;
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    protected LoanEntity() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public Instant getBorrowedAt() { return borrowedAt; }
    public void setBorrowedAt(Instant borrowedAt) { this.borrowedAt = borrowedAt; }
    public Instant getDueAt() { return dueAt; }
    public void setDueAt(Instant dueAt) { this.dueAt = dueAt; }
    public Instant getReturnedAt() { return returnedAt; }
    public void setReturnedAt(Instant returnedAt) { this.returnedAt = returnedAt; }
    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus status) { this.status = status; }
}
