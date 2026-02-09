package com.company.controllers;

import com.company.models.Book;
import com.company.models.Borrow;
import com.company.repositories.LibraryRepositoryImpl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class LibraryControllerImpl {

    private final LibraryRepositoryImpl repo = new LibraryRepositoryImpl();

    public void borrowBook(int userId, int bookId) {
        Book book = repo.getBookById(bookId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.getAvailableCopies() == 0) {
            System.out.println("No available copies.");
            return;
        }

        if (repo.countUserBooks(userId) >= 3) {
            System.out.println("You can't borrow more than 3 books.");
            return;
        }

        repo.borrowBook(userId, bookId);
        System.out.println("Book borrowed successfully.");
    }

    public void returnBook(int userId, int bookId) {
        repo.returnBook(userId, bookId);
        System.out.println("Book returned.");
    }

    public void checkOverdue(int userId) {
        List<Borrow> borrows = repo.getUserBorrows(userId);

        for (Borrow b : borrows) {
            long days = ChronoUnit.DAYS.between(b.getBorrowDate(), LocalDate.now());
            if (days > 14) {
                System.out.println("Book ID " + b.getBookId() + " is OVERDUE (" + days + " days)");
            }
        }
    }
}
