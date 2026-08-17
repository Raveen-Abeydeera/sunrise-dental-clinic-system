package dao;

import database.DatabaseConnection;
import model.Appointment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDAOImpl implements AppointmentDAO {

    @Override
    public boolean addAppointment(Appointment appt) {
        String query = "INSERT INTO appointments (appt_num, name, address, contact, dentist, treatment, appt_date, appt_time, cost) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn == null) return false;
            
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, appt.getApptNumber());
            pstmt.setString(2, appt.getPatientName());
            pstmt.setString(3, appt.getAddress());
            pstmt.setString(4, appt.getContactNumber());
            pstmt.setString(5, appt.getDentistName());
            pstmt.setString(6, appt.getTreatmentType());
            pstmt.setString(7, appt.getDate());
            pstmt.setString(8, appt.getTime());
            pstmt.setDouble(9, appt.getTotalCost());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Appointment getAppointmentByNumber(String apptNumber) {
        String query = "SELECT * FROM appointments WHERE appt_num = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn == null) return null;
            
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, apptNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Appointment(
                    rs.getString("appt_num"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("contact"),
                    rs.getString("dentist"),
                    rs.getString("treatment"),
                    rs.getString("appt_date"),
                    rs.getString("appt_time"),
                    rs.getDouble("cost")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Returns null if not found
    }
}
