package com.company;

import com.company.controllers.UserController;

import java.util.Scanner;

public class MyApplication {

    private final Scanner scanner = new Scanner(System.in);
    private final UserController controller;

    public MyApplication(UserController controller) {
        this.controller = controller;
    }

    public void start() {
        while (true) {
            mainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    System.out.println(controller.getAllUsers());
                    break;

                case 2:
                    System.out.print("Enter user id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println(controller.getUser(id));
                    break;

                case 3:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter surname: ");
                    String surname = scanner.nextLine();

                    System.out.print("Enter gender (male/female): ");
                    String gender = scanner.nextLine();

                    System.out.println(controller.createUser(name, surname, gender));
                    break;

                case 0:
                    System.out.println("Bye!");
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void mainMenu() {
        System.out.println();
        System.out.println("=== MENU ===");
        System.out.println("1. Get all users");
        System.out.println("2. Get user by id");
        System.out.println("3. Create user");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }
}

