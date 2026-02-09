package com.company;

import com.company.controllers.LibraryControllerImpl;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        LibraryControllerImpl controller = new LibraryControllerImpl();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Borrow book");
            System.out.println("2. Return book");
            System.out.println("3. Check overdue");
            System.out.println("0. Exit");

            int choice = sc.nextInt();
            if (choice == 0) break;

            System.out.print("User ID: ");
            int userId = sc.nextInt();

            if (choice == 1) {
                System.out.print("Book ID: ");
                int bookId = sc.nextInt();
                controller.borrowBook(userId, bookId);
            }

            if (choice == 2) {
                System.out.print("Book ID: ");
                int bookId = sc.nextInt();
                controller.returnBook(userId, bookId);
            }

            if (choice == 3) {
                controller.checkOverdue(userId);
            }
        }
    }
}
