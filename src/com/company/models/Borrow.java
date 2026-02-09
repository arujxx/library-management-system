package com.company.models;

import java.time.LocalDate;

public class Borrow {
    private int userId;
    private int bookId;
    private LocalDate borrowDate;

    public Borrow(int userId, int bookId, LocalDate borrowDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }
}
