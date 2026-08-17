package controller;

import model.Appointment;
import dao.AppointmentDAO;
import dao.AppointmentDAOImpl;
import java.util.HashMap;

public class ClinicController {
    
    private final HashMap<String, Double> treatmentPrices;
    private final AppointmentDAO appointmentDAO; 

    public ClinicController() {
        // Simulating a catalog for Bill Calculations
        treatmentPrices = new HashMap<>();
        treatmentPrices.put("Cleaning", 2500.00);
        treatmentPrices.put("Root Canal", 15000.00);
        treatmentPrices.put("Extraction", 5000.00);
        treatmentPrices.put("Whitening", 12000.00);
        
        appointmentDAO = new AppointmentDAOImpl(); 
    }

    public boolean authenticateUser(String username, String password) {
        // Hardcoded auth for demo. Real systems would check a UserDAO.
        return username.equals("admin") && password.equals("sunrise123");
    }

    public double calculateTreatmentCost(String treatmentType) {
        return treatmentPrices.getOrDefault(treatmentType, 0.0);
    }

    public boolean registerAppointment(Appointment appt) {
        // Basic Business Validation
        if (appt.getPatientName().isEmpty() || appt.getContactNumber().isEmpty()) {
            return false;
        }
        return appointmentDAO.addAppointment(appt);
    }
    
    public Appointment searchAppointment(String apptNum) {
        return appointmentDAO.getAppointmentByNumber(apptNum);
    }
}
