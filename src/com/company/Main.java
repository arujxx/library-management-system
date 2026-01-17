package com.company;

import com.company.controllers.UserController;
import com.company.data.PostgresDB;
import com.company.data.interfaces.IDB;
import com.company.repositories.UserRepository;
import com.company.repositories.interfaces.IUserRepository;

public class Main {
    public static void main(String[] args) {

        IDB db = new PostgresDB(
                "jdbc:postgresql://localhost:5432",
                "postgres",
                "0000",
                "librarydb"
        );

        IUserRepository repo = new UserRepository(db);
        UserController controller = new UserController(repo);

        System.out.println(
                controller.createUser(
                        "Aruzhan",
                        "Kenzhebulatova",
                        "female"
                )
        );

        System.out.println(controller.getUser(1));
        System.out.println(controller.getAllUsers());
    }
}
