package com.company.repositories;

import com.company.data.interfaces.IDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LibraryRepository {


    private final IDB idb;

    public LibraryRepository(IDB idb) {
        this.idb = idb;
    }

    // 1. Показать все книги
    public void showBooks() {
        String sql = """
                SELECT id, title, author, total_copies, available_copies
                FROM books
                ORDER BY id
                """;

        try (Connection con = idb.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int total = rs.getInt("total_copies");
                int available = rs.getInt("available_copies");

                String status = available > 0 ? "Доступна" : "Нет в наличии";

                System.out.println(
                        id + " | " +
                                title + " | " +
                                author + " | " +
                                status +
                                " (" + available + "/" + total + ")"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2. Поиск по названию
    public void searchByTitle(String title) {
        String sql = """
                SELECT id, title, author, total_copies, available_copies
                FROM books
                WHERE title ILIKE ?
                """;

        try (Connection con = idb.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + title + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("title") + " | " +
                                rs.getString("author")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. Взять книгу
    public void borrowBook(int id, String name) {
        String sql = """
                UPDATE books
                SET available_copies = available_copies - 1
                WHERE id = ? AND available_copies > 0
                """;

        try (Connection con = idb.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int updated = ps.executeUpdate();

            if (updated > 0) {
                System.out.println("Книга выдана пользователю " + name);
            } else {
                System.out.println("Книга недоступна");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 4. Вернуть книгу
    public void returnBook(int id) {
        String sql = """
                UPDATE books
                SET available_copies = available_copies + 1
                WHERE id = ? AND available_copies < total_copies
                """;

        try (Connection con = idb.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Книга возвращена");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 5. Добавить книгу
    public void addBook(String title) {
        String sql = """
                INSERT INTO books (title, author, total_copies, available_copies)
                VALUES (?, 'Unknown', 1, 1)
                """;

        try (Connection con = idb.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.executeUpdate();
            System.out.println("Книга добавлена");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
