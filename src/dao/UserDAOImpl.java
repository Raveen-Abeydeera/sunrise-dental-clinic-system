package dao;

import database.DatabaseConnection;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public User authenticate(String username, String password) {
        String query = "SELECT username, role, password_hash FROM users WHERE username = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn == null) return null;
            
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String dbHash = rs.getString("password_hash");
                String inputHash = util.SecurityUtil.hashPassword(password);
                
                // Graceful migration: Checks if it matches the hash OR the legacy plain text password
                if (dbHash.equals(inputHash) || dbHash.equals(password)) {
                    return new User(
                        rs.getString("username"), 
                        dbHash, 
                        rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


   @Override
    public boolean registerUser(String username, String password, String role, String fullName, String email) {
        String query = "INSERT INTO users (username, password_hash, role, full_name, email) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn == null) return false;
            
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);          
            pstmt.setString(2, util.SecurityUtil.hashPassword(password)); 
            pstmt.setString(3, role.toLowerCase()); 
            pstmt.setString(4, fullName);
            pstmt.setString(5, email);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}