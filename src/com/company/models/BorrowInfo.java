package com.company.models;

import java.time.LocalDate;

public class BorrowInfo {

    public String borrowerName;
    public String title;
    public String author;
    public String category;
    public LocalDate borrowDate;
    public LocalDate returnDate;
    public String status;

    public BorrowInfo(String borrowerName,
                      String title,
                      String author,
                      String category,
                      LocalDate borrowDate,
                      LocalDate returnDate,
                      String status) {

        this.borrowerName = borrowerName;
        this.title = title;
        this.author = author;
        this.category = category;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }
}
