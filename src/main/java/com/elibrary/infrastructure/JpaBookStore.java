package com.elibrary.infrastructure;

import com.elibrary.application.BookStore;
import com.elibrary.domain.book.Book;
import com.elibrary.domain.book.ContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaBookStore implements BookStore {
    private final BookJpaRepository books;

    public JpaBookStore(BookJpaRepository books) {
        this.books = books;
    }

    @Override
    public Page<Book> search(String keyword, ContentType type, Pageable pageable) {
        return books.search(keyword, type, pageable).map(JpaBookStore::toDomain);
    }

    @Override
    public Optional<Book> findById(String id) {
        return books.findById(id).map(JpaBookStore::toDomain);
    }

    @Override
    public Optional<Book> lockById(String id) {
        return books.lockById(id).map(JpaBookStore::toDomain);
    }

    @Override
    public void save(Book book) {
        books.save(toEntity(book));
    }

    static Book toDomain(BookEntity entity) {
        return new Book(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getIsbn(),
                entity.getContentType(), entity.getSummary(), entity.getLicenseCount(),
                entity.getAvailableSeats(), entity.getContentUrl());
    }

    static BookEntity toEntity(Book book) {
        BookEntity entity = new BookEntity();
        entity.setId(book.id());
        entity.setTitle(book.title());
        entity.setAuthor(book.author());
        entity.setIsbn(book.isbn());
        entity.setContentType(book.contentType());
        entity.setSummary(book.summary());
        entity.setLicenseCount(book.licenseCount());
        entity.setAvailableSeats(book.availableSeats());
        entity.setContentUrl(book.contentUrl());
        return entity;
    }
}
