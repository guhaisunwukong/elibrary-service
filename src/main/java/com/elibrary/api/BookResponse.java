package com.elibrary.api;

import com.elibrary.domain.book.Book;
import com.elibrary.domain.book.ContentType;

public class BookResponse {
    private String id;
    private String title;
    private String author;
    private String isbn;
    private ContentType contentType;
    private String summary;
    private int licenseCount;
    private int availableSeats;
    private String contentUrl;

    public static BookResponse of(Book book, boolean includeContent) {
        BookResponse response = new BookResponse();
        response.id = book.id();
        response.title = book.title();
        response.author = book.author();
        response.isbn = book.isbn();
        response.contentType = book.contentType();
        response.summary = book.summary();
        response.licenseCount = book.licenseCount();
        response.availableSeats = book.availableSeats();
        response.contentUrl = includeContent ? book.contentUrl() : null;
        return response;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public ContentType getContentType() { return contentType; }
    public String getSummary() { return summary; }
    public int getLicenseCount() { return licenseCount; }
    public int getAvailableSeats() { return availableSeats; }
    public String getContentUrl() { return contentUrl; }
}
