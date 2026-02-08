package com.company.Services;

import java.util.List;

public interface IUserService {
    boolean addUser(User user);
    User getUserById(int id);
    List<User> getAllUsers();
    boolean deleteUser(int id);
}
