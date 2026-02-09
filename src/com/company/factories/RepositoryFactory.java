package com.company.factories;

import com.company.data.interfaces.IDB;
import com.company.repositories.BookRepository;
import com.company.repositories.BorrowRepository;
import com.company.repositories.CategoryRepository;
import com.company.repositories.UserRepository;

public class RepositoryFactory {

    private final IDB db;

    public RepositoryFactory(IDB db) {
        this.db = db;
    }

    public BookRepository bookRepo() {
        return new BookRepository(db);
    }

    public BorrowRepository borrowRepo() {
        return new BorrowRepository(db);
    }

    public CategoryRepository categoryRepo() {
        return new CategoryRepository(db);
    }

    public UserRepository userRepo() {
        return new UserRepository(db);
    }
}
