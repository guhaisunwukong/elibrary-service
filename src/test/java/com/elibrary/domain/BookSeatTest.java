package com.elibrary.domain;

import com.elibrary.domain.book.Book;
import com.elibrary.domain.book.ContentType;
import com.elibrary.domain.loan.Loan;
import com.elibrary.domain.loan.LoanAlreadyReturnedException;
import com.elibrary.domain.loan.LoanOwnerMismatchException;
import com.elibrary.domain.loan.NoAvailableSeatException;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookSeatTest {
    @Test
    void lastSeatCanBeTakenOnce() {
        Book book = book(1);
        book.takeSeat();
        assertEquals(0, book.availableSeats());
        assertThrows(NoAvailableSeatException.class, book::takeSeat);
    }

    @Test
    void returnReleasesASeatUpToTheLicense() {
        Book book = book(1);
        book.takeSeat();
        book.releaseSeat();
        book.releaseSeat();
        assertEquals(1, book.availableSeats());
    }

    @Test
    void onlyTheBorrowerCanReturnAnActiveLoan() {
        Instant now = Instant.parse("2026-10-26T00:00:00Z");
        Loan loan = Loan.open("l1", "book-clean", "alice", now, 14);
        assertThrows(LoanOwnerMismatchException.class, () -> loan.giveBack("bob", now.plusSeconds(60)));
        loan.giveBack("alice", now.plusSeconds(60));
        assertNotNull(loan.returnedAt());
        assertThrows(LoanAlreadyReturnedException.class, () -> loan.giveBack("alice", now.plusSeconds(120)));
    }

    private static Book book(int seats) {
        return new Book("book-clean", "Clean Architecture", "Robert C. Martin", "9780134494166",
                ContentType.EBOOK, "summary", seats, seats, "/content/book-clean");
    }
}
