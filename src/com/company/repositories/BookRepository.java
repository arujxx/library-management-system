package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private final IDB db;

    public BookRepository(IDB db) {
        this.db = db;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        String sql =
                "SELECT b.id, b.title, b.author, c.name AS category, b.available_copies " +
                        "FROM books b " +
                        "JOIN categories c ON b.category_id = c.id";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getInt("available_copies")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    public void updateAvailableCopies(int bookId, int newCount) {
        String sql = "UPDATE books SET available_copies = ? WHERE id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, newCount);
            ps.setInt(2, bookId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
