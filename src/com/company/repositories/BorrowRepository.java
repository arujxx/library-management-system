package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.BorrowInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowRepository {

    private final IDB db;

    public BorrowRepository(IDB db) {
        this.db = db;
    }

    public void createBorrow(int bookId, String name) {
        String sql =
                "INSERT INTO borrow (book_id, borrower_name, borrow_date, status) " +
                        "VALUES (?, ?, CURRENT_DATE, 'BORROWED')";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            ps.setString(2, name);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnBorrow(int bookId) {
        String sql =
                "UPDATE borrow SET return_date = CURRENT_DATE, status = 'RETURNED' " +
                        "WHERE book_id = ? AND return_date IS NULL";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BorrowInfo> getAllBorrowInfo() {
        List<BorrowInfo> list = new ArrayList<>();

        String sql =
                "SELECT br.borrower_name, br.borrow_date, br.return_date, br.status, " +
                        "b.title, b.author, c.name AS category " +
                        "FROM borrow br " +
                        "JOIN books b ON br.book_id = b.id " +
                        "JOIN categories c ON b.category_id = c.id";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new BorrowInfo(
                        rs.getString("borrower_name"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getDate("borrow_date").toLocalDate(),
                        rs.getDate("return_date") != null
                                ? rs.getDate("return_date").toLocalDate()
                                : null,
                        rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
