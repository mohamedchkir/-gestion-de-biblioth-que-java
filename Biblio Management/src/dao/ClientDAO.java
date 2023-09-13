package dao;

import dbutil.DBC;

import java.sql.*;

public class ClientDAO {

    public int getClientIdByCIN(String cin) {
        int clientId = -1;

        try {
            Connection conn = DBC.getConnection();

            String sql = "SELECT id FROM client WHERE cin = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cin);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                clientId = resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return clientId;
    }


    public static int createClient(String clientCin,String clientName,String clientPhone,String clientEmail) {
        int clientId = -1;

        try {
            Connection conn = DBC.getConnection();



            // Insert the new client into the database
            String sql = "INSERT INTO client (name, email ,phone ,cin) VALUES (?, ?, ?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, clientName);
            ps.setString(2, clientEmail);
            ps.setString(3, clientPhone);
            ps.setString(4, clientCin);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    clientId = generatedKeys.getInt(1);
                    System.out.println("Client created successfully");
                }
            } else {
                System.out.println("Failed to create a new client.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return clientId;
    }


    public int getClientIdByName(String clientName) {
        int clientId = -1;

        try {
            Connection conn = DBC.getConnection();
            String sql = "SELECT id FROM client WHERE UPPER(name) = UPPER(?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, clientName);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                clientId = resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return clientId;
    }
}
