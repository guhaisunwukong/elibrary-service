package com.elibrary.api;

import com.elibrary.application.BorrowBook;
import com.elibrary.application.ListActiveLoans;
import com.elibrary.application.ReturnBook;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    private final BorrowBook borrowBook;
    private final ReturnBook returnBook;
    private final ListActiveLoans listActiveLoans;

    public LoanController(BorrowBook borrowBook, ReturnBook returnBook, ListActiveLoans listActiveLoans) {
        this.borrowBook = borrowBook;
        this.returnBook = returnBook;
        this.listActiveLoans = listActiveLoans;
    }

    @PostMapping("/loans")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse borrow(@RequestHeader("X-User-Id") String userId, @Valid @RequestBody BorrowRequest request) {
        return LoanResponse.of(borrowBook.borrow(userId, request.getBookId()));
    }

    @PostMapping("/loans/{id}/return")
    public LoanResponse giveBack(@RequestHeader("X-User-Id") String userId, @PathVariable String id) {
        return LoanResponse.of(returnBook.giveBack(userId, id));
    }

    @GetMapping("/me/loans")
    public List<LoanResponse> mine(@RequestHeader("X-User-Id") String userId) {
        return listActiveLoans.list(userId).stream().map(LoanResponse::of).collect(Collectors.toList());
    }
}
