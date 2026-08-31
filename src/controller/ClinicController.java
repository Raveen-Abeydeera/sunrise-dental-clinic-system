package controller;

import model.Appointment;
import dao.AppointmentDAO;
import dao.AppointmentDAOImpl;
import dao.PatientDAO;         
import dao.PatientDAOImpl;     
import dao.UserDAO;            
import dao.UserDAOImpl;        
import java.util.HashMap;
import util.EmailUtil;

public class ClinicController {
    
    private final HashMap<String, Double> treatmentPrices;
    private final AppointmentDAO appointmentDAO;
    
    // New DAO declarations
    private final UserDAO userDAO;          
    private final PatientDAO patientDAO;    

    public ClinicController() {
        // Simulating a catalog for Bill Calculations
        treatmentPrices = new HashMap<>();
        treatmentPrices.put("Cleaning", 2500.00);
        treatmentPrices.put("Root Canal", 15000.00);
        treatmentPrices.put("Extraction", 5000.00);
        treatmentPrices.put("Whitening", 12000.00);
        
        appointmentDAO = new AppointmentDAOImpl();
        
        // Initializing the new DAOs
        userDAO = new UserDAOImpl();          
        patientDAO = new PatientDAOImpl();    
    }

    public boolean authenticateUser(String username, String password) {
        // Now uses the database instead of hardcoded values
        return userDAO.authenticate(username, password) != null;
    }
    
    public boolean registerNewPatient(String patientId, String name, String address, String contact, int age) {
        return patientDAO.registerPatient(patientId, name, address, contact, age);
    }

    public double calculateTreatmentCost(String treatmentType) {
        return treatmentPrices.getOrDefault(treatmentType, 0.0);
    }

    public boolean registerAppointment(Appointment appt) {
        // Basic Business Validation
        if (appt.getPatientName().isEmpty() || appt.getContactNumber().isEmpty()) {
            return false;
        }
        
        boolean isSaved = appointmentDAO.addAppointment(appt);
        
        // If saved successfully in the database, trigger the email alert
        if (isSaved) {
            // Note: In a full system, you would fetch the patient's actual email from the Patient table.
            // For demonstration, we'll pass a placeholder or the contact number field if used as email.
            String patientEmail = "patient@example.com"; 
            
            EmailUtil.sendAppointmentConfirmation(
                patientEmail, 
                appt.getPatientName(), 
                appt.getApptNumber(), 
                appt.getDate(), 
                appt.getTime(), 
                appt.getTotalCost()
            );
        }
        
        return isSaved;
    }
    
    public Appointment searchAppointment(String apptNum) {
        return appointmentDAO.getAppointmentByNumber(apptNum);
    }
    
    public java.util.List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }

    public boolean deleteAppointment(String apptNum) {
        return appointmentDAO.deleteAppointment(apptNum);
    }
}