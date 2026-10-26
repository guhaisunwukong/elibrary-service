package com.elibrary.application;

import com.elibrary.domain.book.Book;
import com.elibrary.domain.book.BookNotFoundException;
import com.elibrary.domain.book.ContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BrowseBooks {
    private static final int MAX_PAGE_SIZE = 50;
    private final BookStore books;

    public BrowseBooks(BookStore books) {
        this.books = books;
    }

    public Page<Book> browse(String keyword, ContentType type, int page, int size) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        String q = keyword == null || keyword.isBlank() ? null : keyword.trim();
        return books.search(q, type, PageRequest.of(safePage, safeSize));
    }

    public Book detail(String bookId) {
        return books.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
    }
}
