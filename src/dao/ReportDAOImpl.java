/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import database.DatabaseConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportDAOImpl implements ReportDAO {

    @Override
    public List<Object[]> getDailyReport(String date) {
        List<Object[]> results = new ArrayList<>();
        // Calling the stored procedure from your sunrise_dental_clinic.sql file
        String query = "{CALL sp_GetDailyReport(?)}"; 
        
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn == null) return results;
            
            CallableStatement stmt = conn.prepareCall(query);
            stmt.setString(1, date); // Pass the date parameter (YYYY-MM-DD)
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                // Mapping the result set to an Object array for the JTable
                results.add(new Object[]{
                    rs.getString("Appointment #"),
                    rs.getString("Patient Name"),
                    rs.getString("Dentist"),
                    rs.getString("Treatment"),
                    rs.getString("Time"),
                    rs.getDouble("Amount"),
                    rs.getString("Status")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
