package com.company.repositories;

import com.company.models.Book;
import java.util.List;

public interface IBookRepository {

    boolean addBook(Book book);

    Book getBookById(int id);

    List<Book> getAllBooks();

    boolean deleteBook(int id);
}
