package com.elibrary.api;

import com.elibrary.domain.book.BookNotFoundException;
import com.elibrary.domain.loan.AlreadyBorrowedException;
import com.elibrary.domain.loan.LoanAlreadyReturnedException;
import com.elibrary.domain.loan.LoanNotFoundException;
import com.elibrary.domain.loan.LoanOwnerMismatchException;
import com.elibrary.domain.loan.NoAvailableSeatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiError> bookMissing(BookNotFoundException ex) {
        return error(HttpStatus.NOT_FOUND, "BOOK_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<ApiError> loanMissing(LoanNotFoundException ex) {
        return error(HttpStatus.NOT_FOUND, "LOAN_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(AlreadyBorrowedException.class)
    public ResponseEntity<ApiError> alreadyBorrowed(AlreadyBorrowedException ex) {
        return error(HttpStatus.CONFLICT, "ALREADY_BORROWED", ex.getMessage());
    }

    @ExceptionHandler(NoAvailableSeatException.class)
    public ResponseEntity<ApiError> noSeat(NoAvailableSeatException ex) {
        return error(HttpStatus.CONFLICT, "NO_AVAILABLE_SEAT", ex.getMessage());
    }

    @ExceptionHandler(LoanAlreadyReturnedException.class)
    public ResponseEntity<ApiError> returned(LoanAlreadyReturnedException ex) {
        return error(HttpStatus.CONFLICT, "LOAN_ALREADY_RETURNED", ex.getMessage());
    }

    @ExceptionHandler(LoanOwnerMismatchException.class)
    public ResponseEntity<ApiError> owner(LoanOwnerMismatchException ex) {
        return error(HttpStatus.FORBIDDEN, "LOAN_OWNER_MISMATCH", ex.getMessage());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiError> header(MissingRequestHeaderException ex) {
        return error(HttpStatus.BAD_REQUEST, "MISSING_USER", "Header X-User-Id is required");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> invalid(MethodArgumentNotValidException ex) {
        return error(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", "bookId is required");
    }

    private static ResponseEntity<ApiError> error(HttpStatus status, String code, String message) {
        return ResponseEntity.status(status).body(new ApiError(code, message));
    }
}
