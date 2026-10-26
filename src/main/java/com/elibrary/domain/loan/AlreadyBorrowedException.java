package com.elibrary.domain.loan;

public class AlreadyBorrowedException extends RuntimeException {
    public AlreadyBorrowedException(String bookId) {
        super("User already has an active loan for book " + bookId);
    }
}
