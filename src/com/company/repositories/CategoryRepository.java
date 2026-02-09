package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    private final IDB db;

    public CategoryRepository(IDB db) {
        this.db = db;
    }

    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT id, name FROM categories";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category c = new Category();
                c.id = rs.getInt("id");
                c.name = rs.getString("name");
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM categories WHERE id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeQuery().next();
        } catch (Exception e) {
            return false;
        }
    }
}
