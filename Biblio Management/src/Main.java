import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.BookDAO;
import models.Book;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

        public static void main(String[]args){
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Welcome To Library Management System");
                System.out.println("1. View All Books");
                System.out.println("2. Add Book");
                System.out.println("3. Update Book");
                System.out.println("4. Delete Book");
                System.out.println("5. Reserve Book");
                System.out.println("6. Return Book");
                System.out.println("7. View Available Books");
                System.out.println("8. View Reserved Books");
                System.out.println("9. Search For Book");
                System.out.println("10. Show Statistics");
                System.out.println("11. Add User");
                System.out.println("12. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        showBook();
                        break;
                    case 2:
                        addBook();

                        break;
                    case 3:
                        updateBook();
                        break;
                    case 4:
                        deleteBook();
                        break;
                    case 5:
                        reserveBook();
                        break;
                    case 6:
                        returnBook();
                        break;
                    case 7:
                        availableBook();
                        break;
                    case 8:
                        borrowBook();
                        break;
                    case 9:
                        searchBook();
                        break;
                    case 10:
                        showStatistics();
                        break;
                    case 11:
                        addClient();
                        break;
                    case 12:
                        System.out.println("Merciiii bcp ");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("ta ryeeeeeeeeeee7");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                }
            }
        }

    private static void addClient() {
    }

    private static void showStatistics() {
    }

    private static void searchBook() {
    }

    private static void borrowBook() {
    }

    private static void availableBook() {
    }

    private static void returnBook() {
    }

    private static void reserveBook() {
    }

    private static void deleteBook() {
    }

    private static void updateBook() {
    }

    private static void addBook() {
    }

    private static void showBook()  {
        System.out.println("-----------------------------------------------");
        
        List<Book> bookList;
        var dao = new BookDAO();
        bookList = dao.showBook();
        for(Book book: bookList)
        {
            displayProduct(book);
        }
        System.out.println("-----------------------------------------------");
        System.out.println("\n");
    }
    public static void displayProduct(Book book)
    {
        System.out.println("Book ID: "+book.getId());
        System.out.println("Book Name: "+book.getTitle());
        System.out.println("Book Isbn: "+book.getIsbn());
        System.out.println("Book Category: "+book.getCategory());
        System.out.println("Book Edition: "+book.getEdition());
        System.out.println("Book Author: "+book.getAuthor().getId());
        System.out.println("Book Quantity: "+book.getQuantity());
        System.out.println("Book Available: "+book.getAvailable());
        System.out.println("Book Borrowed: "+book.getBorrow());
        System.out.println("Book Lost: "+book.getLost());
        System.out.println("\n");
    }
}