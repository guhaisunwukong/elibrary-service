package com.elibrary.application;

import com.elibrary.domain.book.Book;
import com.elibrary.domain.book.ContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookStore {
    Page<Book> search(String keyword, ContentType type, Pageable pageable);

    Optional<Book> findById(String id);

    Optional<Book> lockById(String id);

    void save(Book book);
}
