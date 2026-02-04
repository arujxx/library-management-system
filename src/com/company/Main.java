package com.company;

import com.company.controllers.LibraryControllerImpl;
import com.company.repositories.BorrowRepository;
import com.company.models.BorrowInfo;
import com.company.data.PostgresDB;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        LibraryControllerImpl controller = new LibraryControllerImpl();
        Scanner sc = new Scanner(System.in);


        PostgresDB db = new PostgresDB(
                "localhost",
                "postgres",
                "0000",
                "librarydb"
        );

        try (Connection connection = db.getConnection()) {

            BorrowRepository borrowRepo = new BorrowRepository(connection);

            while (true) {
                System.out.println("\n1. Show books");
                System.out.println("2. Borrow book");
                System.out.println("3. Return book");
                System.out.println("4. Search by title");
                System.out.println("5. Borrow info");
                System.out.println("0. Exit");

                int choice;
                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    System.out.println("Введите число!");
                    continue;
                }

                if (choice == 0) {
                    System.out.println("Bye!");
                    break;
                }

                if (choice == 1) {
                    controller.showBooks();
                }

                if (choice == 2) {
                    System.out.print("Enter book ID: ");
                    int id = Integer.parseInt(sc.nextLine());

                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();

                    controller.borrowBook(id, name);
                }

                if (choice == 3) {
                    System.out.print("Enter book ID: ");
                    int id = Integer.parseInt(sc.nextLine());
                    controller.returnBook(id);
                }

                if (choice == 4) {
                    System.out.print("Enter title: ");
                    String title = sc.nextLine();
                    controller.search(title);
                }

                if (choice == 5) {
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
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        sc.close();
    }
}
