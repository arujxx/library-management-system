package com.company.Services;

import com.company.models.Book;
import java.util.List;

public interface IBookService {
    boolean addBook(Book book);
    Book getBookById(int id);
    List<Book> getAllBooks();
    boolean deleteBook(int id);

    List<Book> searchByTitle(String title);
    List<Book> sortByTitle();
}
