package com.company.Services.impl;

import com.company.Services.IBookService;
import com.company.models.Book;
import com.company.repositories.BookRepository;

import java.util.List;

public class BookServiceImpl implements IBookService {

    private final BookRepository repo;

    public BookServiceImpl(BookRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean addBook(Book book) {
        return false;
    }

    @Override
    public Book getBookById(int id) {
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return repo.getAllBooks();
    }

    @Override
    public boolean deleteBook(int id) {
        return false;
    }

    @Override
    public List<Book> searchByTitle(String title) {
        return null;
    }

    @Override
    public List<Book> sortByTitle() {
        return null;
    }
}

