package dao;

import dbutil.DBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorDAO {



    public  int fetchAuthorId(String authorName) {
        int authorId = 0;
        try {
            Connection conn= DBC.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id FROM auteur WHERE UPPER(name) = UPPER(?)");

            ps.setString(1, authorName);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                authorId = resultSet.getInt("id");
            }
            resultSet.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorId;
    }
}
