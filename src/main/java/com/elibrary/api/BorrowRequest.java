package com.elibrary.api;

import javax.validation.constraints.NotBlank;

public class BorrowRequest {
    @NotBlank
    private String bookId;

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
}
