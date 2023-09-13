package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Author;
import models.Book;
import dbutil.DBC;

public class BookDAO {

    public List<Book> showBook()  {

        List<Book> bookList =new ArrayList<Book>();

        try {
            Connection conn = DBC.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT b.*, a.name AS name FROM book b JOIN auteur a ON b.author_id = a.id");

            while(rs.next()){

                        int id = rs.getInt("id");
                        String title =rs.getString("title");
                        String isbn = rs.getString("isbn");
                        String category = rs.getString("category");
                        String edition = rs.getString("edition");
                        int quantity = rs.getInt("quantity");
                        int available = rs.getInt("available");
                        int borrow = rs.getInt("borrowed");
                        int lost = rs.getInt("lost");
                        int author_id = rs.getInt("author_id");
                        String author_name = rs.getString("name");



                        Book book =new Book(id, title, category, edition, isbn, author_id,author_name, quantity, available, borrow, lost);
                        bookList.add(book);

            }

        } catch (SQLException e){

            e.printStackTrace();
        }
        return bookList;
    }

    public int addBook( Book book ,int author_id){
        int succes = 0;

        try {
            Connection conn= DBC.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO book(`title`,`author_id`,`edition`,`isbn`,`quantity`,`category`,`available`,`borrowed`,`lost`) VALUES(?,?,?,?,?,?,?,?,?)");

            ps.setString(1,book.getTitle());
            ps.setInt(2,author_id);
            ps.setString(3,book.getEdition());
            ps.setString(4,book.getIsbn());
            ps.setInt(5,book.getQuantity());
            ps.setString(6,book.getCategory());
            ps.setInt(7,book.getQuantity());
            ps.setInt(8,0);
            ps.setInt(9,0);

            succes = ps.executeUpdate();

        }catch (SQLException ex){

            ex.printStackTrace();
        }
        return succes;
    }

    public static int deleteBook(int id) {
        int success = 0;
        try
        {
            Connection conn = DBC.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM book where id = ?");

            ps.setInt(1, id);
            success = ps.executeUpdate();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return success;
    }

    public int updateBookByISBN(String isbn, Book newBook, String authorName) {
        int success = 0;

        try {
            Connection conn = DBC.getConnection();


            String fetchSql = "SELECT quantity, borrowed, lost FROM book WHERE isbn = ?";
            PreparedStatement fetchPs = conn.prepareStatement(fetchSql);
            fetchPs.setString(1, isbn);
            ResultSet resultSet = fetchPs.executeQuery();

            if (resultSet.next()) {

                int oldQuantity = resultSet.getInt("quantity");
                int oldBorrowed = resultSet.getInt("borrowed");
                int oldLost = resultSet.getInt("lost");
                int newAvailable = oldQuantity - oldBorrowed - oldLost;


                if (newBook.getQuantity() >= oldQuantity) {

                    AuthorDAO dao = new AuthorDAO();
                    int authorId = dao.fetchAuthorId(authorName);

                    if (authorId != 0) {
                        String updateSql = "UPDATE book SET title=?, author_id=?, edition=?, quantity=?, category=?, available=? WHERE isbn=?";
                        PreparedStatement ps = conn.prepareStatement(updateSql);

                        ps.setString(1, newBook.getTitle());
                        ps.setInt(2, authorId);
                        ps.setString(3, newBook.getEdition());
                        ps.setInt(4, newBook.getQuantity());
                        ps.setString(5, newBook.getCategory());
                        ps.setInt(6, newAvailable);
                        ps.setString(7, isbn);

                        success = ps.executeUpdate();
                    } else {
                        System.out.println("Author with the name " + authorName + " does not exist.");
                    }
                } else {
                    System.out.println("Entered quantity is less than the current quantity. Please enter a higher quantity.");
                }
            } else {
                System.out.println("Book with ISBN " + isbn + " does not exist.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return success;
    }


    public List<Book> searchBooksByTitle(String title) {
        List<Book> matchingBooks = new ArrayList<>();

        try {
            Connection conn = DBC.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT b.*, a.name AS name FROM book b JOIN auteur a ON b.author_id = a.id WHERE UPPER(b.title) LIKE UPPER(?)");
            ps.setString(1, "%" + title + "%" );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String isbn = rs.getString("isbn");
                String category = rs.getString("category");
                String edition = rs.getString("edition");
                int quantity = rs.getInt("quantity");
                int available = rs.getInt("available");
                int borrow = rs.getInt("borrowed");
                int lost = rs.getInt("lost");
                int author_id = rs.getInt("author_id");
                String author_name = rs.getString("name");
                Book book = new Book(id, title, category, edition, isbn, author_id, author_name, quantity, available, borrow, lost);
                matchingBooks.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matchingBooks;
    }


    public List<Book> searchBooksByAuthor(String authorName) {
        List<Book> matchingBooks = new ArrayList<>();

        try {
            Connection conn = DBC.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT b.*, a.name AS name FROM book b JOIN auteur a ON b.author_id = a.id WHERE UPPER (a.name) LIKE UPPER(?)");
            ps.setString(1, "%" + authorName + "%"  );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String title =rs.getString("title");
                String isbn = rs.getString("isbn");
                String category = rs.getString("category");
                String edition = rs.getString("edition");
                int quantity = rs.getInt("quantity");
                int available = rs.getInt("available");
                int borrow = rs.getInt("borrowed");
                int lost = rs.getInt("lost");
                int author_id = rs.getInt("author_id");

                Book book = new Book(id, title, category, edition, isbn, author_id, authorName, quantity, available, borrow, lost);
                matchingBooks.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matchingBooks;
    }



    public int getTotalAvailableBooks() {
        int totalAvailableBooks = 0;

        try (Connection conn = DBC.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT SUM(available) FROM `book`")) {

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                totalAvailableBooks = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return totalAvailableBooks;
    }

    public int getTotalBorrowedBooks() {
        int totalBorrowedBooks = 0;

        try (Connection conn = DBC.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT SUM(borrowed) FROM `book`")) {

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                totalBorrowedBooks = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return totalBorrowedBooks;
    }

    public int getTotalLostBooks() {
        int totalLostBooks = 0;

        try (
             Connection conn = DBC.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT SUM(lost) FROM `book`")) {

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                totalLostBooks = resultSet.getInt(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return totalLostBooks;
    }

    public int getTotalReservations(){
        int TotalReservations = 0;

        try(
                Connection conn =DBC.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM reservation")){

            ResultSet res = ps.executeQuery();

            if (res.next()){
                TotalReservations =res.getInt(1);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return TotalReservations;

    }

    public boolean isBookExistsByISBN(String isbn) {
        boolean exists = false;

        try (Connection conn = DBC.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM `book` WHERE isbn = ?")) {

            ps.setString(1, isbn);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                exists = count > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return exists;
    }









}
