package com.elibrary.application;

import com.elibrary.domain.book.Book;
import com.elibrary.domain.book.BookNotFoundException;
import com.elibrary.domain.loan.Loan;
import com.elibrary.domain.loan.LoanNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@Service
public class ReturnBook {
    private final BookStore books;
    private final LoanStore loans;
    private final Clock clock;

    public ReturnBook(BookStore books, LoanStore loans, Clock clock) {
        this.books = books;
        this.loans = loans;
        this.clock = clock;
    }

    @Transactional
    public Loan giveBack(String userId, String loanId) {
        Loan loan = loans.lockById(loanId).orElseThrow(() -> new LoanNotFoundException(loanId));
        loan.giveBack(userId, clock.instant());
        Book book = books.lockById(loan.bookId()).orElseThrow(() -> new BookNotFoundException(loan.bookId()));
        book.releaseSeat();
        loans.save(loan);
        books.save(book);
        return loan;
    }
}
