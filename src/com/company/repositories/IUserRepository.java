package com.company.repositories;

import com.company.models.User;
import java.util.List;

public interface IUserRepository {
    boolean addUser(User user);
    User getUserById(int id);
    List<User> getAllUsers();
    boolean deleteUser(int id);
}
