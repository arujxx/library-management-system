package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT id, name, email FROM users WHERE email = ?";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRoleByEmail(String email) {
        String sql = "SELECT role FROM users WHERE email = ?";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getRoleById(int id) {
        String sql = "SELECT role FROM users WHERE id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("role");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, name, email FROM users";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
