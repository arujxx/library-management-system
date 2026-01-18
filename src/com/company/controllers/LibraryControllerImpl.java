package com.company.controllers;

import com.company.data.PostgresDB;
import com.company.repositories.LibraryRepository;

public class LibraryControllerImpl {

    private final LibraryRepository repo =
            new LibraryRepository(new PostgresDB(
                    "localhost",
                    "postgres",
                    "keneimba0",
                    "library server"
            ));

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

    public void addBook(String title) {
        repo.addBook(title);
    }
}



