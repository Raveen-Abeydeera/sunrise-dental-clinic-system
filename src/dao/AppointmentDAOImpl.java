package dao;

import database.DatabaseConnection;
import model.Appointment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class AppointmentDAOImpl implements AppointmentDAO {

    @Override
    public boolean addAppointment(Appointment appt) {
        
        String query = "INSERT INTO appointments (appt_num, patient_id, name, address, contact, dentist, treatment, appt_date, appt_time, cost) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn == null) return false;
            
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, appt.getApptNumber());
            pstmt.setString(2, appt.getPatientId());
            pstmt.setString(3, appt.getPatientName());
            pstmt.setString(4, appt.getAddress());
            pstmt.setString(5, appt.getContactNumber());
            pstmt.setString(6, appt.getDentistName());
            pstmt.setString(7, appt.getTreatmentType());
            pstmt.setString(8, appt.getDate());
            pstmt.setString(9, appt.getTime());
            pstmt.setDouble(10, appt.getTotalCost());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT appt_num, name, contact, treatment, cost FROM appointments";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn == null) return appointments;
            
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Appointment appt = new Appointment(
                    rs.getString("appt_num"),
                    "", // patientId omitted for this view
                    rs.getString("name"),
                    "", // address omitted
                    rs.getString("contact"),
                    "", // dentist omitted
                    rs.getString("treatment"),
                    "", // date omitted
                    "", // time omitted
                    rs.getDouble("cost")
                );
                appointments.add(appt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public boolean deleteAppointment(String apptNumber) {
        String query = "DELETE FROM appointments WHERE appt_num = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, apptNumber);
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
                    rs.getString("patient_id"),
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
        return null;
    }
}