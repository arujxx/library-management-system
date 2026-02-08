package com.company;

import com.company.controllers.LibraryControllerImpl;
import com.company.data.PostgresDB;
import com.company.repositories.LibraryRepository;
import com.company.Services.IBookService;
import com.company.Services.impl.BookServiceImpl;
import com.company.repositories.BookRepository;


import java.util.Scanner;

public class MyApplication {

    private User currentUser;

    private final LibraryControllerImpl controller;
    private final Scanner sc;

    private IBookService bookService;



    public MyApplication() {

        PostgresDB db = new PostgresDB(
                "localhost",
                "postgres",
                "0000",
                "librarydb"
        );

        LibraryRepository repo = new LibraryRepository(db);
        controller = new LibraryControllerImpl(repo);

        this.bookService =
                new BookServiceImpl(new BookRepository(db));

        sc = new Scanner(System.in);

        currentUser = new User(1, "Admin", "admin@mail.com");
        currentUser.setRole("ADMIN");

    }


    public void start() {


        while (true) {
            System.out.println("\n1. Show books");
            System.out.println("2. Borrow book");
            System.out.println("3. Return book");
            System.out.println("4. Search by title");
            System.out.println("5. Add book (ADMIN)");
            System.out.println("6. Show books via service");
            System.out.println("7. Service test (for demo)");

            System.out.println("0. Exit");

            String input = sc.nextLine().trim();


            int choice = Integer.parseInt(input);


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

                if (!currentUser.getRole().equals("ADMIN")) {
                    System.out.println("Access denied. Admin only.");
                    continue;
                }

                System.out.print("Enter book title: ");
                String title = sc.nextLine();

                controller.addBook(title);
            }
            if (choice == 6) {
                bookService.getAllBooks();
            }
            if (choice == 7) {
                bookService.getAllBooks();
                bookService.sortByTitle();
                bookService.searchByTitle("test");
            }



        }



        sc.close();
    }


}
