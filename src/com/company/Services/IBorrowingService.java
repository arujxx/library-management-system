package com.company.Services;

import java.util.List;

public interface IBorrowingService {

    boolean borrowBook(Borrow borrow);

    List<Borrow> getAllBorrowings();
}
