package com.elibrary.api;

import com.elibrary.application.BrowseBooks;
import com.elibrary.application.LoanStore;
import com.elibrary.domain.book.Book;
import com.elibrary.domain.book.ContentType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BrowseBooks browseBooks;
    private final LoanStore loans;

    public BookController(BrowseBooks browseBooks, LoanStore loans) {
        this.browseBooks = browseBooks;
        this.loans = loans;
    }

    @GetMapping
    public PageResponse<BookResponse> list(@RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) ContentType type,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size) {
        return PageResponse.of(browseBooks.browse(keyword, type, page, size), book -> BookResponse.of(book, false));
    }

    @GetMapping("/{id}")
    public BookResponse detail(@PathVariable String id,
                               @RequestHeader(value = "X-User-Id", required = false) String userId) {
        Book book = browseBooks.detail(id);
        boolean borrowed = userId != null && !userId.isBlank() && loans.hasActiveLoan(userId, id);
        return BookResponse.of(book, borrowed);
    }
}
