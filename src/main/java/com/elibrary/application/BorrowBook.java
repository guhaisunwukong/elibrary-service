package com.elibrary.application;

import com.elibrary.domain.book.Book;
import com.elibrary.domain.book.BookNotFoundException;
import com.elibrary.domain.loan.AlreadyBorrowedException;
import com.elibrary.domain.loan.Loan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
public class BorrowBook {
    private final BookStore books;
    private final LoanStore loans;
    private final Clock clock;
    private final int loanDays;

    public BorrowBook(BookStore books, LoanStore loans, Clock clock,
                      @Value("${elibrary.loan-days:14}") int loanDays) {
        this.books = books;
        this.loans = loans;
        this.clock = clock;
        this.loanDays = loanDays;
    }

    @Transactional
    public Loan borrow(String userId, String bookId) {
        Book book = books.lockById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        if (loans.hasActiveLoan(userId, bookId)) {
            throw new AlreadyBorrowedException(bookId);
        }
        book.takeSeat();
        Loan loan = Loan.open(UUID.randomUUID().toString(), bookId, userId, clock.instant(), loanDays);
        books.save(book);
        return loans.save(loan);
    }
}
