package dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDAOImpl implements PatientDAO {

    @Override
    public boolean registerPatient(String patientId, String fullName, String address, String contactNumber, int age) {
        String query = "INSERT INTO patients (patient_id, full_name, address, contact_number, date_of_birth) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn == null) return false;
            
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, patientId);
            pstmt.setString(2, fullName);
            pstmt.setString(3, address);
            pstmt.setString(4, contactNumber);
            
            pstmt.setString(5, "1990-01-01"); 
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkPatientExists(String patientId) {
        String query = "SELECT patient_id FROM patients WHERE patient_id = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}