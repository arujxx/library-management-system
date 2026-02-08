package com.company.repositories;

public interface ILibraryRepository {

    void showBooks();
    void searchByTitle(String title);
    void borrowBook(int bookId, String borrowerName);
    void returnBook(int bookId);
    void addBook(String title);
}
