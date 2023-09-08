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

    public int updateBook(Book book ,int id) {
        int success = 0;

        try {
            Connection conn = DBC.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE book SET title=?, author_id=?, edition=?, isbn=?, quantity=?, category=?, available=?, borrowed=?, lost=? WHERE id=?");

            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getAuthor().getId());
            ps.setString(3, book.getEdition());
            ps.setString(4, book.getIsbn());
            ps.setInt(5, book.getQuantity());
            ps.setString(6, book.getCategory());
            ps.setInt(7, book.getAvailable());
            ps.setInt(8, book.getBorrow());
            ps.setInt(9, book.getLost());
            ps.setInt(10, book.getId()); // Assuming 'id' is the primary key of the book

            success = ps.executeUpdate();

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
            ps.setString(1, title );

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
            ps.setString(1, authorName );

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




}
