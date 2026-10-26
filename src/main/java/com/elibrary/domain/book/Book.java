package com.elibrary.domain.book;

import com.elibrary.domain.loan.NoAvailableSeatException;

public final class Book {
    private final String id;
    private final String title;
    private final String author;
    private final String isbn;
    private final ContentType contentType;
    private final String summary;
    private final int licenseCount;
    private int availableSeats;
    private final String contentUrl;

    public Book(String id, String title, String author, String isbn, ContentType contentType,
                String summary, int licenseCount, int availableSeats, String contentUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.contentType = contentType;
        this.summary = summary;
        this.licenseCount = licenseCount;
        this.availableSeats = availableSeats;
        this.contentUrl = contentUrl;
    }

    public void takeSeat() {
        if (availableSeats <= 0) {
            throw new NoAvailableSeatException(id);
        }
        availableSeats--;
    }

    public void releaseSeat() {
        if (availableSeats < licenseCount) {
            availableSeats++;
        }
    }

    public String id() { return id; }
    public String title() { return title; }
    public String author() { return author; }
    public String isbn() { return isbn; }
    public ContentType contentType() { return contentType; }
    public String summary() { return summary; }
    public int licenseCount() { return licenseCount; }
    public int availableSeats() { return availableSeats; }
    public String contentUrl() { return contentUrl; }
}
