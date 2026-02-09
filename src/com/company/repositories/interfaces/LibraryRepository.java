package com.company.repositories.interfaces;

import com.company.models.Book;
import com.company.models.Borrow;
import java.util.List;

public interface LibraryRepository {
    Book getBookById(int id);
    boolean borrowBook(int userId, int bookId);
    boolean returnBook(int userId, int bookId);
    int countUserBooks(int userId);
    List<Borrow> getUserBorrows(int userId);
}
