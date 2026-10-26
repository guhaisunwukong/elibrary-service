package com.elibrary.domain.loan;

public class NoAvailableSeatException extends RuntimeException {
    public NoAvailableSeatException(String bookId) {
        super("No seat left for book " + bookId);
    }
}
