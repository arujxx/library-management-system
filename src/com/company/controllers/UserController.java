package com.company.controllers;

import java.util.List;

import com.company.models.User;
import com.company.repositories.interfaces.IUserRepository;

public class UserController {

    private final IUserRepository repo;

    public UserController(IUserRepository repo) {
        this.repo = repo;
    }

    public String createUser(String name, String surname, String gender) {
        User user = new User(name, surname, gender);
        boolean created = repo.createUser(user);
        return created ? "User was created!" : "User creation failed!";
    }

    public String getUser(int id) {
        User user = repo.getUser(id);
        return user == null ? "User not found!" : user.toString();
    }

    public String getAllUsers() {
        StringBuilder result = new StringBuilder();

        for (User user : repo.getAllUsers()) {
            result.append(user).append("\n");
        }

        if (result.isEmpty()) {
            return "No users found";
        }

        return result.toString();
    }
}
