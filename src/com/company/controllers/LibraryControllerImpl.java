package com.company.controllers;

import com.company.repositories.ILibraryRepository;

public class LibraryControllerImpl {

    private final ILibraryRepository repo;

    public LibraryControllerImpl(ILibraryRepository repo) {
        this.repo = repo;
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

    public void addBook(String title) {
        repo.addBook(title);
    }
}
