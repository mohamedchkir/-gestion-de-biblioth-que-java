package dao;

import dbutil.DBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class BorrowDAO{

    public int borrowBook( int clientId ,String bookIsbn) {
        int success = 0;

        try {
            Connection conn = DBC.getConnection();


            String checkSql = "SELECT COUNT(*) FROM reservation WHERE client_id = ? AND book_isbn = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setInt(1, clientId);
            checkPs.setString(2, bookIsbn);
            ResultSet resultSet = checkPs.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count == 0) {
                    Date currentDate = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);
                    calendar.add(Calendar.DAY_OF_YEAR, 8);
                    Date endDate = calendar.getTime();

                    String sql = "INSERT INTO reservation (book_isbn, client_id, start_date, end_date) VALUES (?, ?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, bookIsbn);
                    ps.setInt(2, clientId);
                    ps.setDate(3, new java.sql.Date(currentDate.getTime()));
                    ps.setDate(4, new java.sql.Date(endDate.getTime()));

                    success = ps.executeUpdate();
                } else {
                    System.out.println("Duplicate reservation detected. Cannot reserve the same book again.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return success;
    }



    public  void updateReserve(String isbn) {
        // SQL update statement
        String sql = "UPDATE book SET borrowed = borrowed + 1, available = available - 1 WHERE isbn = ?";

        try (
                Connection conn = DBC.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            // Set the ISBN parameter
            ps.setString(1, isbn);

            // Execute the update
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book reserved successfully.");
            } else {
                System.out.println("No book found with the given ISBN.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public String returnReservedBook(int clientId, String bookIsbn) {
        String bookTitle = null;

        try {
            Connection conn = DBC.getConnection();

            // Check if there is a reservation matching the client ID and book ISBN
            String selectSql = "SELECT b.title FROM reservation r " +
                    "INNER JOIN book b ON r.book_isbn = b.isbn " +
                    "WHERE r.client_id = ? AND r.book_isbn = ?";

            PreparedStatement selectPs = conn.prepareStatement(selectSql);
            selectPs.setInt(1, clientId);
            selectPs.setString(2, bookIsbn);

            // Execute the select statement to get the book title
            ResultSet resultSet = selectPs.executeQuery();

            if (resultSet.next()) {
                bookTitle = resultSet.getString("title");

                // Delete the reservation
                String deleteSql = "DELETE FROM reservation WHERE client_id = ? AND book_isbn = ?";
                PreparedStatement deletePs = conn.prepareStatement(deleteSql);
                deletePs.setInt(1, clientId);
                deletePs.setString(2, bookIsbn);
                deletePs.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return bookTitle;
    }


    public  void updateReturn(String isbn) {
        // SQL update statement
        String sql = "UPDATE book SET borrowed = borrowed - 1, available = available + 1 WHERE isbn = ?";

        try (
                Connection conn = DBC.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, isbn);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("No book found with the given ISBN.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
