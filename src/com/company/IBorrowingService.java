package com.company;

import java.util.List;
import com.company.models.Borrow;

public interface IBorrowingService {

    boolean borrowBook(Borrow borrow);

    List<Borrow> getAllBorrowings();
}
