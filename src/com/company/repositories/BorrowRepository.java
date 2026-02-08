package com.company.repositories;

import com.company.models.BorrowInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowRepository {

    private final Connection connection;

    public BorrowRepository(Connection connection) {
        this.connection = connection;
    }

    public void createBorrow(int bookId, String borrowerName) throws SQLException {
        String sql =
                "INSERT INTO borrow (book_id, borrower_name, borrow_date, status) " +
                        "VALUES (?, ?, CURRENT_DATE, 'BORROWED')";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, bookId);
        ps.setString(2, borrowerName);
        ps.executeUpdate();
    }

    public void returnBorrow(int bookId) throws SQLException {
        String sql =
                "UPDATE borrow SET return_date = CURRENT_DATE, status = 'RETURNED' " +
                        "WHERE book_id = ? AND return_date IS NULL";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, bookId);
        ps.executeUpdate();
    }

    public List<BorrowInfo> getAllBorrowInfo() throws SQLException {

        List<BorrowInfo> list = new ArrayList<>();

        String sql =
                "SELECT br.borrower_name, br.borrow_date, br.return_date, br.status, " +
                        "b.title, b.author, c.name AS category " +
                        "FROM borrow br " +
                        "JOIN books b ON br.book_id = b.id " +
                        "JOIN categories c ON b.category_id = c.id";

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

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

        return list;
    }
}
