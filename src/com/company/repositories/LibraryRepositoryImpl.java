package com.company.repositories;

import com.company.data.PostgresDB;
import com.company.models.Book;
import com.company.models.Borrow;
import com.company.repositories.interfaces.LibraryRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryRepositoryImpl implements LibraryRepository {

    private final Connection connection = PostgresDB.getConnection();

    @Override
    public Book getBookById(int id) {
        try {
            String sql = "SELECT * FROM books WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("available_copies")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int countUserBooks(int userId) {
        try {
            String sql = "SELECT COUNT(*) FROM borrows WHERE user_id=? AND return_date IS NULL";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean borrowBook(int userId, int bookId) {
        try {
            String insert = "INSERT INTO borrows(user_id, book_id, borrow_date) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insert);
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.executeUpdate();

            String update = "UPDATE books SET available_copies = available_copies - 1 WHERE id=?";
            PreparedStatement stmt2 = connection.prepareStatement(update);
            stmt2.setInt(1, bookId);
            stmt2.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean returnBook(int userId, int bookId) {
        try {
            String sql = "UPDATE borrows SET return_date=? WHERE user_id=? AND book_id=? AND return_date IS NULL";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setInt(2, userId);
            stmt.setInt(3, bookId);
            stmt.executeUpdate();

            String update = "UPDATE books SET available_copies = available_copies + 1 WHERE id=?";
            PreparedStatement stmt2 = connection.prepareStatement(update);
            stmt2.setInt(1, bookId);
            stmt2.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Borrow> getUserBorrows(int userId) {
        List<Borrow> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM borrows WHERE user_id=? AND return_date IS NULL";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Borrow(
                        userId,
                        rs.getInt("book_id"),
                        rs.getDate("borrow_date").toLocalDate()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
