package model;

public class Appointment {
    private String apptNumber;
    private String patientId; // Added to match Database
    private String patientName;
    private String address;
    private String contactNumber;
    private String dentistName;
    private String treatmentType;
    private String date;
    private String time;
    private double totalCost;

    public Appointment(String apptNumber, String patientId, String patientName, String address, String contact,
                        String dentist, String treatment, String date, String time, double totalCost) {
        this.apptNumber = apptNumber;
        this.patientId = patientId;
        this.patientName = patientName;
        this.address = address;
        this.contactNumber = contact;
        this.dentistName = dentist;
        this.treatmentType = treatment;
        this.date = date;
        this.time = time;
        this.totalCost = totalCost;
    }

    public String getApptNumber() { return apptNumber; }
    public String getPatientId() { return patientId; }
    public String getPatientName() { return patientName; }
    public String getAddress() { return address; }
    public String getContactNumber() { return contactNumber; }
    public String getDentistName() { return dentistName; }
    public String getTreatmentType() { return treatmentType; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public double getTotalCost() { return totalCost; }
}