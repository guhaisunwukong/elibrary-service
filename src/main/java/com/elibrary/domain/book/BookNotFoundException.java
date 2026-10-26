package com.elibrary.domain.book;

public class BookNotFoundException extends RuntimeException {
    private final String bookId;

    public BookNotFoundException(String bookId) {
        super("Book not found: " + bookId);
        this.bookId = bookId;
    }

    public String bookId() {
        return bookId;
    }
}
