package dao;

import model.Appointment;

public interface AppointmentDAO {
    boolean addAppointment(Appointment appt);
    Appointment getAppointmentByNumber(String apptNumber);
}
