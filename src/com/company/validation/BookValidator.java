package com.company.validation;

import com.company.models.Book;

public class BookValidator implements Validator<Book> {

    @Override
    public boolean isValid(Book book) {
        if (book == null) return false;

        if (book.getTitle() == null || book.getTitle().trim().isEmpty())
            return false;

        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty())
            return false;

        if (book.getCategory() == null || book.getCategory().trim().isEmpty())
            return false;

        if (book.getAvailableCopies() < 0)
            return false;

        return true;
    }
}

