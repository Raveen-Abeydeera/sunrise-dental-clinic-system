package dao;

import model.Appointment;
import java.util.List;

public interface AppointmentDAO {
    boolean addAppointment(Appointment appt);
    Appointment getAppointmentByNumber(String apptNumber);
    
    // Add these two new methods:
    List<Appointment> getAllAppointments();
    boolean deleteAppointment(String apptNumber);
}