import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.*;

import dao.AuthorDAO;
import dao.BookDAO;
import dao.BorrowDAO;
import dao.ClientDAO;
import models.Author;
import models.Book;



// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {



    static Scanner sc =new Scanner(System.in);

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                clearScreen();


                System.out.println(centerText("\n"));
                System.out.println(centerText("\n"));
                System.out.println(centerText("-----------------------------"));
                System.out.println(centerText("| Library Management System |"));
                System.out.println(centerText("-----------------------------"));
                System.out.println(centerText("\n"));

                System.out.println(centerText("-----------------------------------------------"));
                System.out.println(centerText("1. View All Books"));
                System.out.println(centerText("2. Add Book"));
                System.out.println(centerText("3. Update Book"));
                System.out.println(centerText("4. Delete Book"));
                System.out.println(centerText("5. Reserve Book"));
                System.out.println(centerText("6. Return Book"));
                System.out.println(centerText("7. View Available Books"));
                System.out.println(centerText("8. View Reserved Books"));
                System.out.println(centerText("9. Search For Book"));
                System.out.println(centerText("10. Show Statistics"));
                System.out.println(centerText("11. Exit"));
                System.out.println(centerText("-----------------------------------------------"));

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
                        System.out.println("Thank uuuuuu <3");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("select an exist choice");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                }
            }
        }


    private static String centerText(String text) {
            int terminalWidth = 200;
            int padding = (terminalWidth - text.length()) / 2;
            return " ".repeat(Math.max(0, padding)) + text;
        }


    private static void clearScreen() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }



    private static void showStatistics() {

        BookDAO dao = new BookDAO();
        int totalAvailableBooks = dao.getTotalAvailableBooks();
        int totalBorrowedBooks = dao.getTotalBorrowedBooks();
        int totalLostBooks = dao.getTotalLostBooks();
        int totalReservations = dao.getTotalReservations();


        System.out.println(centerText("\n"));
        System.out.println(centerText("\n"));
        System.out.println(centerText("-----------------------------------------------"));
        System.out.println(centerText("Total Available Books: " + totalAvailableBooks));
        System.out.println(centerText("Total Borrowed Books: " + totalBorrowedBooks));
        System.out.println(centerText("Total Lost Books: "+ totalLostBooks));
        System.out.println(centerText("Total Reservations: "+ totalReservations));
        System.out.println(centerText("-----------------------------------------------"));


    }

    private static void searchBook() {
        System.out.println("------------------------------------------------");
        System.out.println("Search by:");
        System.out.println("1. Book Title");
        System.out.println("2. Author Name");
        System.out.println("------------------------------------------------");
        int searchOption = sc.nextInt();
        sc.nextLine();

        if (searchOption == 1) {
            System.out.println("Enter Book Title:");
            String title = sc.nextLine();
            searchBooksByTitle(title);
        } else if (searchOption == 2) {
            System.out.println("Enter Author Name:");
            String authorName = sc.nextLine();
            searchBooksByAuthor(authorName);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void searchBooksByTitle(String title) {
        var dao = new BookDAO();
        List<Book> books = dao.searchBooksByTitle(title);

        if (books.isEmpty()) {
            System.out.println("No books found with the given title.");
        } else {
            System.out.println("Books found with the title '" + title + "':");
            for (Book book : books) {
                displayBook(book);
            }
        }
    }

    private static void searchBooksByAuthor(String authorName) {
        var dao = new BookDAO();
        List<Book> books = dao.searchBooksByAuthor(authorName);

        if (books.isEmpty()) {
            System.out.println("No books found by the author '" + authorName + "'.");
        } else {
            System.out.println("Books found by the author '" + authorName + "':");
            for (Book book : books) {
                displayBook(book);
            }
        }
    }


    private static void borrowBook() {

        System.out.println("\n");
        System.out.println("\n");
        System.out.println(centerText("-------------------------------------") );
        System.out.println(centerText("|  Book Name  |   ISBN   | Borrowed |"));
        System.out.println(centerText("-------------------------------------") );


        List<Book> bookList;
        var dao = new BookDAO();
        bookList = dao.showBook();

        for (Book book : bookList) {
            displayBorrowedBook(book);
        }
        System.out.println(centerText("-------------------------------------") );
        System.out.println("\n");
    }

    private static void availableBook() {

        System.out.println("\n");
        System.out.println("\n");
        System.out.println(centerText("-------------------------------------") );
        System.out.println(centerText("|  Book Name  |   ISBN   | Available |"));
        System.out.println(centerText("-------------------------------------") );


        List<Book> bookList;
        var dao = new BookDAO();
        bookList = dao.showBook();

        for (Book book : bookList) {
            displayAvailableBook(book);
        }
        System.out.println(centerText("-------------------------------------") );
        System.out.println("\n");
    }

    private static void returnBook() {

        System.out.println("------------------------------------------------");
        System.out.println("Enter Client Name:");
        System.out.println("------------------------------------------------");
        String clientName = sc.nextLine();

        System.out.println("------------------------------------------------");
        System.out.println("Enter Book ISBN:");
        System.out.println("------------------------------------------------");
        String bookIsbn = sc.nextLine();

        ClientDAO clientDAO = new ClientDAO();
        int clientId = clientDAO.getClientIdByName(clientName);

        if (clientId != -1) {
            BorrowDAO dao = new BorrowDAO();
            String returnedBookTitle = dao.returnReservedBook(clientId, bookIsbn);

            if (returnedBookTitle != null) {
                dao.updateReturn(bookIsbn);
            } else {
                System.out.println("No reservation found for the given client and book ISBN.");
            }
        } else {
            System.out.println("Client with the given name not found.");
        }

    }



    private static void reserveBook() {
        System.out.println("------------------------------------------------");
        System.out.println("Enter Client CIN:");
        System.out.println("------------------------------------------------");
        String clientCin = sc.nextLine();

        System.out.println("------------------------------------------------");
        System.out.println("Enter Book ISBN:");
        System.out.println("------------------------------------------------");
        String bookIsbn = sc.nextLine();

        // Check if the book exists
        BookDAO B_dao = new BookDAO();
        if (B_dao.isBookExistsByISBN(bookIsbn)) {
            // Check if the book is available
            if (B_dao.isBookAvailableByISBN(bookIsbn)) {
                ClientDAO C_dao = new ClientDAO();
                int clientId = C_dao.getClientIdByCIN(clientCin);

                if (clientId == -1) {
                    System.out.println("------------------------------------------------");
                    System.out.println("Enter Client Name:");
                    System.out.println("------------------------------------------------");
                    String clientName = sc.nextLine();

                    System.out.println("------------------------------------------------");
                    System.out.println("Enter Client phone:");
                    System.out.println("------------------------------------------------");
                    String clientPhone = sc.nextLine();

                    System.out.println("------------------------------------------------");
                    System.out.println("Enter Client email:");
                    System.out.println("------------------------------------------------");
                    String clientEmail = sc.nextLine();

                    clientId = ClientDAO.createClient(clientCin, clientName, clientPhone, clientEmail);
                }

                BorrowDAO dao = new BorrowDAO();
                int result = dao.borrowBook(clientId, bookIsbn);

                if (result == 1) {
                    dao.updateReserve(bookIsbn);
                    System.out.println("Reservation successful.");
                } else {
                    System.out.println("Reservation failed. Please check the client CIN and book ISBN.");
                }
            } else {
                System.out.println("The book with ISBN " + bookIsbn + " is not available for reservation.");
            }
        } else {
            System.out.println("The book with ISBN " + bookIsbn + " does not exist.");
        }
    }



    private static void updateBook() {
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("------------------------------------------------");
        System.out.println("Enter Book ISBN:");
        System.out.println("------------------------------------------------");
        String isbn = sc.nextLine();


        Book updatedBook = new Book();

        System.out.println("Enter New Book Title:");
        updatedBook.setTitle(sc.nextLine());

        System.out.println("Enter New Book Category:");
        updatedBook.setCategory(sc.nextLine());

        System.out.println("Enter New Book Edition:");
        updatedBook.setEdition(sc.nextLine());

        System.out.println("Enter Author Name:");
        String authorName = sc.nextLine();

        System.out.println("Enter New Book Quantity:");
        updatedBook.setQuantity(sc.nextInt());


        sc.nextLine();


        BookDAO dao = new BookDAO();
        int status = dao.updateBookByISBN(isbn, updatedBook, authorName);

        if (status == 1) {
            System.out.println(centerText("Book updated successfully."));
        } else {
            System.out.println(centerText("Error while updating book."));
        }



    }

    private static void addBook()  {

        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");


        System.out.println("------------------------------------------------");
        System.out.println("Enter Book Isbn:");
        System.out.println("------------------------------------------------");
        String isbn = sc.nextLine();

        while (isbn.isEmpty()) {
            System.out.println("Isbn cannot be empty. Please enter a valid Isbn:");
            isbn = sc.nextLine();
        }


        var daob = new BookDAO();

        if (daob.isBookExistsByISBN(isbn)) {
            System.out.println("A book with ISBN " + isbn + " already exists.");
        } else {
            System.out.println("------------------------------------------------");
            System.out.println("Enter Book Title:");
            String title = sc.nextLine();
            while (title.isEmpty()) {
                System.out.println("Title cannot be empty. Please enter a valid title:");
                title = sc.nextLine();
            }
            System.out.println("------------------------------------------------");
            System.out.println("Enter Book Category:");
            System.out.println("------------------------------------------------");
            String category = sc.nextLine();
            while (category.isEmpty()) {
                System.out.println("Category cannot be empty. Please enter a valid title:");
                category = sc.nextLine();
            }

            System.out.println("------------------------------------------------");
            System.out.println("Enter Book edition:");
            System.out.println("------------------------------------------------");
            String edition = sc.nextLine();
            while (edition.isEmpty()) {
                System.out.println("edition cannot be empty. Please enter a valid title:");
                edition = sc.nextLine();
            }

            System.out.println("------------------------------------------------");
            System.out.println("Enter Book author name:");
            System.out.println("------------------------------------------------");

            String authorName = sc.nextLine();
            while (authorName.isEmpty()) {
                System.out.println(" Author name cannot be empty. Please enter a valid title:");
                authorName = sc.nextLine();
            }

            var dao = new AuthorDAO();

            int authorId = dao.fetchAuthorId(authorName);

            if (authorId == 0) {
                System.out.println("Author not found.");
            }



            System.out.println("------------------------------------------------");
            System.out.println("Enter Book quantity:");
            System.out.println("------------------------------------------------");
            String input = sc.nextLine();

            while (input.isEmpty()) {
                System.out.println("Quantity cannot be empty. Please enter a valid quantity:");
                input = sc.nextLine();
            }

            int quantity = Integer.parseInt(input);

            Book book = new Book(title, category,edition, authorId,isbn,quantity);


            int status = daob.addBook(book ,authorId);
            if(status ==1 )
            {
                System.out.println("Book added successfully");
            }
            else
            {
                System.out.println("ERROR while adding Book");
            }
            System.out.println("\n");

        }


    }

    private static void showBook() {


        System.out.println("\n");
        System.out.println("\n");
        System.out.println(centerText("-------------------------------------------------------------------------------------------------------------------") );
        System.out.println(centerText("| Book ID |  Book Name  |   ISBN   | Category |  Edition  |   Author   | Quantity | Available | Borrowed |  Lost  |"));
        System.out.println(centerText("-------------------------------------------------------------------------------------------------------------------") );


        List<Book> bookList;
        var dao = new BookDAO();
        bookList = dao.showBook();

        for (Book book : bookList) {
            displayBook(book);
        }
        System.out.println(centerText("-------------------------------------------------------------------------------------------------------------------") );
        System.out.println("\n");
    }

    public static void displayBook(Book book) {
        String formattedString = String.format("| %-8s| %-12s| %-10s| %-9s| %-10s| %-11s| %-9s| %-10s| %-9s| %-6s|",
                book.getId(), book.getTitle(), book.getIsbn(), book.getCategory(),
                book.getEdition(), book.getAuthor().getName(), book.getQuantity(),
                book.getAvailable(), book.getBorrow(), book.getLost());
        System.out.println(centerText(formattedString));
    }
    public static void displayAvailableBook(Book book) {
        String formattedString = String.format("| %-12s| %-10s| %-9s|",
                 book.getTitle(), book.getIsbn(),
                book.getAvailable());
        System.out.println(centerText(formattedString));
    }

    public static void displayBorrowedBook(Book book) {
        String formattedString = String.format("| %-12s| %-10s| %-9s|",
                 book.getTitle(), book.getIsbn(),
                book.getBorrow());
        System.out.println(centerText(formattedString));
    }

    public static void deleteBook() {
        System.out.println("------------------------------------------------");
        System.out.println("Enter Book ISBN:");
        System.out.println("------------------------------------------------");
        String bookIsbn = sc.nextLine();

        // Check if the book with the given ISBN exists
        BookDAO B_dao = new BookDAO();
        if (B_dao.isBookExistsByISBN(bookIsbn)) {
            System.out.println("Are you sure you want to delete this book?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int confirmation = sc.nextInt();

            if (confirmation == 1) {
                int status = B_dao.deleteBookByISBN(bookIsbn);
                if (status == 1) {
                    System.out.println("Book deleted successfully");
                } else {
                    System.out.println("Something went wrong");
                }
            } else if (confirmation == 2) {
                System.out.println("Deletion canceled.");
            } else {
                System.out.println("Invalid choice. Deletion canceled.");
            }
        } else {
            System.out.println("The book with ISBN " + bookIsbn + " does not exist.");
        }

        System.out.println("\n");
    }


}