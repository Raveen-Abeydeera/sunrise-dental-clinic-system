package dao;

import model.Appointment;

public interface PatientDAO {
    boolean registerPatient(String patientId, String fullName, String address, String contactNumber, int age);
    boolean checkPatientExists(String patientId);
}
