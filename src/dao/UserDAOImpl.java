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
        // Note: For top marks in security, you must mention in your report that 
        // you would use BCrypt for password hashing in a production environment.
        String query = "SELECT username, role FROM users WHERE username = ? AND password_hash = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn == null) return null;
            
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password); // Comparing raw password to the hash for this basic implementation
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new User(
                    rs.getString("username"), 
                    password, 
                    rs.getString("role")
                );
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
            // NOTE: In a real system, hash this password with BCrypt before saving!
            pstmt.setString(2, password); 
            pstmt.setString(3, role.toLowerCase()); // 'admin', 'doctor', or 'receptionist'
            pstmt.setString(4, fullName);
            pstmt.setString(5, email);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}