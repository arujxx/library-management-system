package com.company.controllers;

import com.company.models.Book;
import com.company.repositories.LibraryRepository;
import com.company.validation.BookValidator;
import com.company.validation.Validator;

import java.sql.Connection;

public class LibraryControllerImpl {

    private final LibraryRepository repo;
    private final Validator<Book> bookValidator;

    public LibraryControllerImpl(Connection connection) {
        this.repo = new LibraryRepository(connection);
        this.bookValidator = new BookValidator();
    }

    public void showBooks() {
        repo.showBooks();
    }

    public void search(String title) {
        repo.searchByTitle(title);
    }

    public void borrowBook(int id, String name) {
        repo.borrowBook(id, name);
    }

    public void returnBook(int id) {
        repo.returnBook(id);
    }

    // ✅ ВАЛИДАЦИЯ BOOK
    public void addBook(Book book) {
        if (!bookValidator.isValid(book)) {
            System.out.println("Invalid book data");
            return;
        }

        // repo.addBook ждёт String
        repo.addBook(book.getTitle());
    }

}

