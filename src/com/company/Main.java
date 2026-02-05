package com.company;

import com.company.controllers.LibraryControllerImpl;
import com.company.repositories.BorrowRepository;
import com.company.data.PostgresDB;
import com.company.models.BorrowInfo;
import com.company.models.Book;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Создаем объект подключения к базе
        PostgresDB db = new PostgresDB(
                "localhost",   // хост
                "postgres",    // пользователь
                "keneimba0",   // пароль
                "librarydb"    // имя базы
        );

        Connection connection = null;
        try {
            connection = db.getConnection();
            System.out.println("Connected to database successfully!");

            LibraryControllerImpl controller = new LibraryControllerImpl(connection);
            BorrowRepository borrowRepo = new BorrowRepository(connection);

            boolean running = true;
            while (running) {
                System.out.println("\n1. Show books");
                System.out.println("2. Borrow book");
                System.out.println("3. Return book");
                System.out.println("4. Search by title");
                System.out.println("5. Borrow info");
                System.out.println("6. Add book"); // ✅ NEW
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");

                int choice;
                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    System.out.println("Введите число!");
                    continue;
                }

                switch (choice) {
                    case 0 -> {
                        System.out.println("Bye!");
                        running = false;
                    }
                    case 1 -> controller.showBooks();
                    case 2 -> {
                        System.out.print("Enter book ID: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter your name: ");
                        String name = sc.nextLine();
                        controller.borrowBook(id, name);
                    }
                    case 3 -> {
                        System.out.print("Enter book ID: ");
                        int id = Integer.parseInt(sc.nextLine());
                        controller.returnBook(id);
                    }
                    case 4 -> {
                        System.out.print("Enter title: ");
                        String title = sc.nextLine();
                        controller.search(title);
                    }
                    case 5 -> {
                        List<BorrowInfo> list = borrowRepo.getAllBorrowInfo();
                        if (list.isEmpty()) {
                            System.out.println("No borrow records.");
                        } else {
                            for (BorrowInfo info : list) {
                                System.out.println(
                                        info.borrowerName + " | " +
                                                info.title + " | " +
                                                info.author + " | " +
                                                info.category + " | " +
                                                info.status
                                );
                            }
                        }
                    }

                    // ✅ NEW CASE: addBook(Book)
                    case 6 -> {
                        System.out.print("Enter title: ");
                        String title = sc.nextLine();

                        System.out.print("Enter author: ");
                        String author = sc.nextLine();

                        System.out.print("Enter category: ");
                        String category = sc.nextLine();

                        System.out.print("Enter available copies: ");
                        int copies;
                        try {
                            copies = Integer.parseInt(sc.nextLine());
                        } catch (Exception e) {
                            System.out.println("Copies must be a number!");
                            break;
                        }

                        // id ставим 0 (или любой), если в БД SERIAL — он сам назначится при INSERT
                        Book book = new Book(0, title, author, category, copies);

                        controller.addBook(book);
                    }

                    default -> System.out.println("Invalid choice!");
                }
            }

        } catch (Exception e) {
            System.err.println("Failed to connect to the database!");
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            sc.close();
        }
    }
}
