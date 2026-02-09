package com.company.repositories;

import com.company.data.interfaces.IDB;

import java.sql.*;

public class FullBookRepository {

    private final IDB db;

    public FullBookRepository(IDB db) {
        this.db = db;
    }

    public void printFullBookDescription(int id) {
        String sql =
                "SELECT b.title, b.author, c.name AS category, " +
                        "b.available_copies, b.total_copies " +
                        "FROM books b " +
                        "JOIN categories c ON b.category_id = c.id " +
                        "WHERE b.id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println(
                        rs.getString("title") + " | " +
                                rs.getString("author") + " | " +
                                rs.getString("category") + " | " +
                                rs.getInt("available_copies") + "/" +
                                rs.getInt("total_copies")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
