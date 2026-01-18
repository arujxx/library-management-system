package com.company.repositories;

import com.company.data.interfaces.IDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LibraryRepository {

    private final IDB idb;

    // ВОТ ЭТОГО КОНСТРУКТОРА У ТЕБЯ НЕ БЫЛО
    public LibraryRepository(IDB idb) {
        this.idb = idb;
    }

    public void showBooks() {
        String sql = "SELECT id, title, available, borrowed_by FROM books ORDER BY id";

        try (Connection con = idb.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                boolean available = rs.getBoolean("available");
                String borrowedBy = rs.getString("borrowed_by");

                if (borrowedBy == null) borrowedBy = "";

                System.out.println(
                        id + " | " + title + " | " +
                                (available ? "Свободна" : "Занята") +
                                " | " + borrowedBy
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchByTitle(String title) {
        String sql = "SELECT id, title, available, borrowed_by FROM books WHERE title ILIKE ?";

        try (Connection con = idb.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + title + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("title") + " | " +
                                (rs.getBoolean("available") ? "Свободна" : "Занята") + " | " +
                                (rs.getString("borrowed_by") == null ? "" : rs.getString("borrowed_by"))
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrowBook(int id, String name) {
        String sql = "UPDATE books SET available = false, borrowed_by = ? WHERE id = ?";

        try (Connection con = idb.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();

            System.out.println("Книга забронирована");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnBook(int id) {
        String sql = "UPDATE books SET available = true, borrowed_by = '' WHERE id = ?";

        try (Connection con = idb.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Книга возвращена");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBook(String title) {
        String sql = "INSERT INTO books(title, available, borrowed_by) VALUES (?, true, '')";

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

