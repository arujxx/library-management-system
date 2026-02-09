package com.company;

import com.company.data.DBConfig;
import com.company.data.DBSingleton;
import com.company.factories.RepositoryFactory;
import com.company.models.BorrowInfo;
import com.company.security.Role;
import com.company.security.Session;
import com.company.Validation.Validator;

import java.util.List;
import java.util.Scanner;

public class MyApplication {

    private final RepositoryFactory factory;
    private final Scanner sc = new Scanner(System.in);

    public MyApplication() {
        DBConfig cfg = new DBConfig(
                "localhost",
                "postgres",
                "0000",
                "librarydb"
        );
        factory = new RepositoryFactory(DBSingleton.getInstance(cfg));
    }

    public void run() {
        login();

        while (true) {
            printMenu();
            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> showBooks();
                case "2" -> borrowBook();
                case "3" -> returnBook();
                case "4" -> showBorrowInfo();
                case "5" -> addBookAdmin();
                case "0" -> {
                    System.out.println("Goodbye.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }




    private void login() {
        try {
            System.out.print("Enter your user id: ");
            int userId = Validator.parsePositiveInt(sc.nextLine());

            String roleStr = factory.userRepo().getRoleById(userId);

            if (roleStr == null) {
                System.out.println("User not found. Guest mode.");
                return;
            }

            Role role = Role.valueOf(roleStr);
            Session.getInstance().login(null, role);

            System.out.println("Logged in as " + role);

        } catch (Exception e) {
            System.out.println("Login failed. Guest mode.");
        }
    }




    private void printMenu() {
        System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
        System.out.println("1. Show books");
        System.out.println("2. Borrow book");
        System.out.println("3. Return book");
        System.out.println("4. Borrow history");

        if (Session.getInstance().hasRole(Role.ADMIN)) {
            System.out.println("5. Add book (ADMIN)");
        }

        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }





    private void showBooks() {
        System.out.println("\n--- BOOK LIST ---");
        factory.bookRepo().getAllBooks().forEach(b ->
                System.out.println(
                        b.getId() + ". " +
                                b.getTitle() + " | " +
                                b.getAuthor() + " | " +
                                b.getCategory() + " | Available: " +
                                b.getAvailableCopies()
                )
        );
    }

    private void borrowBook() {
        try {
            System.out.print("Enter book id: ");
            int id = Validator.parsePositiveInt(sc.nextLine());

            System.out.print("Enter your name: ");
            String name = sc.nextLine();

            if (Validator.isBlank(name)) {
                System.out.println("Name cannot be empty.");
                return;
            }

            factory.borrowRepo().createBorrow(id, name);
            System.out.println("Book borrowed.");

        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    private void returnBook() {
        try {
            System.out.print("Enter book id: ");
            int id = Validator.parsePositiveInt(sc.nextLine());

            factory.borrowRepo().returnBorrow(id);
            System.out.println("Book returned.");

        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    private void showBorrowInfo() {
        System.out.println("\n--- BORROW HISTORY ---");

        List<BorrowInfo> list = factory.borrowRepo().getAllBorrowInfo();

        if (list.isEmpty()) {
            System.out.println("No records.");
            return;
        }

        list.forEach(i ->
                System.out.println(
                        i.borrowerName + " | " +
                                i.title + " | " +
                                i.author + " | " +
                                i.category + " | " +
                                i.status
                )
        );
    }

    private void addBookAdmin() {
        if (!Session.getInstance().hasRole(Role.ADMIN)) {
            System.out.println("Access denied. Admin only.");
            return;
        }

        try {
            System.out.print("Title: ");
            String title = sc.nextLine();

            System.out.print("Author: ");
            String author = sc.nextLine();

            if (Validator.isBlank(title) || Validator.isBlank(author)) {
                System.out.println("Invalid input.");
                return;
            }

            System.out.println("Categories:");
            factory.categoryRepo().getAll()
                    .forEach(c -> System.out.println(c.id + ". " + c.name));

            System.out.print("Category id: ");
            int categoryId = Validator.parsePositiveInt(sc.nextLine());

            if (!factory.categoryRepo().existsById(categoryId)) {
                System.out.println("Category not found.");
                return;
            }

            factory.bookRepo().addBook(title, author, categoryId);
            System.out.println("Book added successfully.");

        } catch (Exception e) {
            System.out.println("Error while adding book.");
        }
    }
}
