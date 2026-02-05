package com.company.models;

import java.time.LocalDate;

public class Borrow {

    private int id;
    private int bookId;
    private int userId;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public Borrow(int bookId, int userId, LocalDate borrowDate) {
        this.bookId = bookId;
        this.userId = userId;
        this.borrowDate = borrowDate;
    }

    public int getBookId() {
        return bookId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }
}

