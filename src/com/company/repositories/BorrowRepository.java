package com.company.repositories;

import com.company.models.Borrow;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BorrowRepository {

    private final Connection connection;

    public BorrowRepository(Connection connection) {
        this.connection = connection;
    }

    public void createBorrow(Borrow borrow) throws SQLException {
        String sql = "INSERT INTO borrows (book_id, user_id, borrow_date) VALUES (?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, borrow.getBookId());
        ps.setInt(2, borrow.getUserId());
        ps.setDate(3, Date.valueOf(borrow.getBorrowDate()));
        ps.executeUpdate();
    }

    public void returnBorrow(int borrowId) throws SQLException {
        String sql = "UPDATE borrows SET return_date = ? WHERE id = ? AND return_date IS NULL";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDate(1, Date.valueOf(LocalDate.now()));
        ps.setInt(2, borrowId);
        ps.executeUpdate();
    }

    public int countActiveBorrows() throws SQLException {
        String sql = "SELECT COUNT(*) FROM borrows WHERE return_date IS NULL";

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public ResultSet findOverdueBorrows() throws SQLException {
        String sql = "SELECT * FROM borrows WHERE return_date IS NULL AND borrow_date < ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDate(1, Date.valueOf(LocalDate.now().minusDays(14)));
        return ps.executeQuery();
    }
}
