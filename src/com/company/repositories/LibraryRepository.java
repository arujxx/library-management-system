package com.company.repositories;

import com.company.data.PostgresDB;

import com.company.repositories.ILibraryRepository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryRepository implements ILibraryRepository {

    private final Connection connection;

    public LibraryRepository(PostgresDB db) {
        this.connection = db.getConnection();
    }

    public void showBooks() {
        String sql =
                "SELECT b.id, b.title, b.author, c.name AS category, " +
                        "b.available_copies, b.total_copies " +
                        "FROM books b " +
                        "JOIN categories c ON b.category_id = c.id";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.printf(
                        "%-3d | %-35s | %-20s | %-15s | %d/%d%n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getInt("available_copies"),
                        rs.getInt("total_copies")
                );

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void searchByTitle(String title) {
        String sql =
                "SELECT id, title, author " +
                        "FROM books WHERE LOWER(title) LIKE LOWER(?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("title") + " | " +
                                rs.getString("author")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void borrowBook(int bookId, String borrowerName) {
        String sql =
                "INSERT INTO borrow (book_id, borrower_name, borrow_date, status) " +
                        "VALUES (?, ?, CURRENT_DATE, 'BORROWED')";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ps.setString(2, borrowerName);
            ps.executeUpdate();
            System.out.println("Книга выдана пользователю " + borrowerName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(int bookId) {
        String sql =
                "UPDATE borrow SET return_date = CURRENT_DATE, status = 'RETURNED' " +
                        "WHERE book_id = ? AND return_date IS NULL";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            int updated = ps.executeUpdate();

            if (updated > 0) {
                System.out.println("Книга возвращена");
            } else {
                System.out.println("Активная выдача не найдена");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBook(String title) {
        String sql =
                "INSERT INTO books (title, author, total_copies, available_copies) " +
                        "VALUES (?, 'Unknown', 1, 1)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.executeUpdate();
            System.out.println("Книга добавлена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

