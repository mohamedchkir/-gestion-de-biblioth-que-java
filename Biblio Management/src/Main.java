import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import dao.AuthorDAO;
import dao.BookDAO;
import dao.BorrowDAO;
import models.Author;
import models.Book;

import java.text.SimpleDateFormat;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static Scanner sc =new Scanner(System.in);
        public static void main(String[]args) throws IOException, SQLException {
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
        System.out.println("------------------------------------------------");
        System.out.println("Search by:");
        System.out.println("1. Book Title");
        System.out.println("2. Author Name");
        System.out.println("------------------------------------------------");
        int searchOption = sc.nextInt();
        sc.nextLine(); // Consume the newline character left by nextInt()

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
    }

    private static void availableBook() {
    }

    private static void returnBook() {

        System.out.println("------------------------------------------------");
        System.out.println("Enter Client CIN:");
        System.out.println("------------------------------------------------");
        int clientId = sc.nextInt();
        sc.nextLine();

        System.out.println("------------------------------------------------");
        System.out.println("Enter Book ISBN:");
        System.out.println("------------------------------------------------");
        String bookIsbn = sc.nextLine();

        BorrowDAO dao = new BorrowDAO();
        String returnedBookTitle = dao.returnReservedBook(clientId, bookIsbn);

        if (returnedBookTitle != null) {
            dao.updateReturn(bookIsbn);
        } else {
            System.out.println("No reservation found for the given client ID and book ISBN.");
        }

        sc.close();
    }



    private static void reserveBook() {

        System.out.println("------------------------------------------------");
        System.out.println("Enter Client ID:");
        System.out.println("------------------------------------------------");
        int clientId = sc.nextInt();
        sc.nextLine();

        System.out.println("------------------------------------------------");
        System.out.println("Enter Book ISBN:");
        System.out.println("------------------------------------------------");
        String bookIsbn = sc.nextLine();


        BorrowDAO dao = new BorrowDAO();
        int result = dao.borrowBook(clientId, bookIsbn);

        if (result == 1) {
            dao.updateReserve(bookIsbn);
        } else {
            System.out.println("Please check the client ID and book ISBN.");
        }
    }




    private static void updateBook() {
        System.out.println("------------------------------------------------");
        System.out.println("Enter Book ID:");
        System.out.println("------------------------------------------------");
        int id = sc.nextInt();
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
        System.out.println("------------------------------------------------");
        System.out.println("Enter Book edition:");
        System.out.println("------------------------------------------------");
        String edition = sc.nextLine();
        System.out.println("------------------------------------------------");
        System.out.println("Enter Book author id:");
        System.out.println("------------------------------------------------");
        int author = sc.nextInt();
        sc.nextLine();
        System.out.println("------------------------------------------------");
        System.out.println("Enter Book Isbn:");
        System.out.println("------------------------------------------------");
        String isbn = sc.nextLine();
        System.out.println("------------------------------------------------");
        System.out.println("Enter Book available:");
        System.out.println("------------------------------------------------");
        int available = sc.nextInt();
        System.out.println("------------------------------------------------");
        System.out.println("Enter Book borrowed:");
        System.out.println("------------------------------------------------");
        int borrowed = sc.nextInt();
        System.out.println("------------------------------------------------");
        System.out.println("Enter Book lost:");
        System.out.println("------------------------------------------------");
        int lost = sc.nextInt();
        System.out.println("------------------------------------------------");
        System.out.println("Enter Book quantity:");
        System.out.println("------------------------------------------------");
        int quantity = sc.nextInt();

        //after user enters values, store them in a Book variable
        Book book = new Book(id,title, category,edition, author,isbn,quantity,available,borrowed,lost);

        var dao = new BookDAO();

        int status = dao.updateBook(book , id);
        if(status ==1 )
        {
            System.out.println("Book updated successfully");
        }
        else
        {
            System.out.println("ERROR while updating Book");
        }
        System.out.println("\n");

    }

    private static void addBook() throws IOException {

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
        System.out.println("Enter Book Isbn:");
        System.out.println("------------------------------------------------");
        String isbn = sc.nextLine();

        while (isbn.isEmpty()) {
            System.out.println("Isbn cannot be empty. Please enter a valid Isbn:");
            isbn = sc.nextLine();
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

        var daob = new BookDAO();

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

    private static void showBook()  {
        System.out.println("-----------------------------------------------");

        List<Book> bookList;
        var dao = new BookDAO();
        bookList = dao.showBook();
        for(Book book: bookList)
        {
            displayBook(book);
        }
        System.out.println("-----------------------------------------------");
        System.out.println("\n");
    }
    public static void displayBook(Book book) {
        System.out.println("Book ID: "+book.getId());
        System.out.println("Book Name: "+book.getTitle());
        System.out.println("Book Isbn: "+book.getIsbn());
        System.out.println("Book Category: "+book.getCategory());
        System.out.println("Book Edition: "+book.getEdition());
        System.out.println("Book Author: "+book.getAuthor().getName());
        System.out.println("Book Quantity: "+book.getQuantity());
        System.out.println("Book Available: "+book.getAvailable());
        System.out.println("Book Borrowed: "+book.getBorrow());
        System.out.println("Book Lost: "+book.getLost());
        System.out.println("\n");
    }
    public static void deleteBook() {
        System.out.println("------------------------------------------------");
        System.out.println("Enter Book Id:");
        System.out.println("------------------------------------------------");
        int bookId = sc.nextInt();

        System.out.println("Are you sure you want to delete this book?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int confirmation = sc.nextInt();

        if (confirmation == 1) {
            int status = dao.BookDAO.deleteBook(bookId);
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

        System.out.println("\n");
    }

}