package com.company.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryRepository {

    private final Connection connection;

    public LibraryRepository(Connection connection) {
        this.connection = connection;
    }

    // 1) Показать все книги
    public void showBooks() {
        String sql = """
                SELECT book_id, title, author, category, available_copies
                FROM books
                ORDER BY book_id
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nID | Title | Author | Category | Available");
            System.out.println("----------------------------------------------------------");

            boolean any = false;
            while (rs.next()) {
                any = true;
                int id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String category = rs.getString("category");
                int copies = rs.getInt("available_copies");

                System.out.println(id + " | " + title + " | " + author + " | " + category + " | " + copies);
            }

            if (!any) {
                System.out.println("No books found.");
            }

        } catch (SQLException e) {
            System.out.println("Error while showing books:");
            e.printStackTrace();
        }
    }

    // 2) Поиск по названию
    public void searchByTitle(String title) {
        String sql = """
                SELECT book_id, title, author, category, available_copies
                FROM books
                WHERE LOWER(title) LIKE LOWER(?)
                ORDER BY book_id
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");

            try (ResultSet rs = ps.executeQuery()) {
                boolean any = false;

                System.out.println("\nSearch results:");
                System.out.println("ID | Title | Author | Category | Available");
                System.out.println("----------------------------------------------------------");

                while (rs.next()) {
                    any = true;
                    int id = rs.getInt("book_id");
                    String t = rs.getString("title");
                    String author = rs.getString("author");
                    String category = rs.getString("category");
                    int copies = rs.getInt("available_copies");

                    System.out.println(id + " | " + t + " | " + author + " | " + category + " | " + copies);
                }

                if (!any) {
                    System.out.println("No matches.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error while searching:");
            e.printStackTrace();
        }
    }

    // 3) Выдать книгу (уменьшаем available_copies и записываем borrow)
    public void borrowBook(int bookId, String borrowerName) {

        // 3.1) Проверяем, есть ли доступные копии
        String checkSql = "SELECT available_copies FROM books WHERE book_id = ?";
        String updateSql = "UPDATE books SET available_copies = available_copies - 1 WHERE book_id = ?";

        // Таблица выдачи (типичный вариант)
        String insertBorrowSql = """
                INSERT INTO borrowings (book_id, borrower_name, status)
                VALUES (?, ?, 'BORROWED')
                """;

        try {
            connection.setAutoCommit(false);

            int copies;
            try (PreparedStatement check = connection.prepareStatement(checkSql)) {
                check.setInt(1, bookId);
                try (ResultSet rs = check.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Book not found.");
                        connection.rollback();
                        return;
                    }
                    copies = rs.getInt("available_copies");
                }
            }

            if (copies <= 0) {
                System.out.println("No available copies.");
                connection.rollback();
                return;
            }

            try (PreparedStatement upd = connection.prepareStatement(updateSql)) {
                upd.setInt(1, bookId);
                upd.executeUpdate();
            }

            try (PreparedStatement ins = connection.prepareStatement(insertBorrowSql)) {
                ins.setInt(1, bookId);
                ins.setString(2, borrowerName);
                ins.executeUpdate();
            }

            connection.commit();
            System.out.println("Book borrowed successfully!");

        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ignored) {}
            System.out.println("Error while borrowing:");
            e.printStackTrace();
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }

    // 4) Вернуть книгу (увеличиваем available_copies и обновляем borrow)
    public void returnBook(int bookId) {

        String updateBookSql = "UPDATE books SET available_copies = available_copies + 1 WHERE book_id = ?";

        // Возвращаем одну последнюю активную запись
        String updateBorrowSql = """
                UPDATE borrowings
                SET status = 'RETURNED'
                WHERE borrowing_id = (
                    SELECT borrowing_id
                    FROM borrowings
                    WHERE book_id = ? AND status = 'BORROWED'
                    ORDER BY borrowing_id DESC
                    LIMIT 1
                )
                """;

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement ps1 = connection.prepareStatement(updateBookSql)) {
                ps1.setInt(1, bookId);
                int changed = ps1.executeUpdate();
                if (changed == 0) {
                    System.out.println("Book not found.");
                    connection.rollback();
                    return;
                }
            }

            try (PreparedStatement ps2 = connection.prepareStatement(updateBorrowSql)) {
                ps2.setInt(1, bookId);
                ps2.executeUpdate();
            }

            connection.commit();
            System.out.println("Book returned successfully!");

        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ignored) {}
            System.out.println("Error while returning:");
            e.printStackTrace();
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }

    // 5) Добавить книгу (минимально: только title)
    // ⚠️ Если в твоей таблице author/category/available_copies NOT NULL,
    // то нужно сделать addBook(Book book) и вставлять все поля.
    public void addBook(String title) {
        String sql = "INSERT INTO books (title) VALUES (?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.executeUpdate();
            System.out.println("Book added successfully!");
        } catch (SQLException e) {
            System.out.println("Error while adding book:");
            e.printStackTrace();
        }
    }
}
