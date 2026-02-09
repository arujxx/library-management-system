package com.company.Services;

import com.company.models.Borrow;   // ← ОБЯЗАТЕЛЬНО
import java.util.List;

public interface IBorrowingService {

    boolean borrowBook(Borrow borrow);

    List<Borrow> getAllBorrowings();
}
