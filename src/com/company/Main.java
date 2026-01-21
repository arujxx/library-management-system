package com.company;

import com.company.controllers.LibraryControllerImpl;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LibraryControllerImpl controller = new LibraryControllerImpl();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Show books");
            System.out.println("2. Borrow book");
            System.out.println("3. Return book");
            System.out.println("4. Search by title");
            System.out.println("0. Exit");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Введите число!");
                continue;
            }

            if (choice == 0) break;

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
        }

        sc.close();
    }
}
