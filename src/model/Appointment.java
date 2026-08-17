package model;

public class Appointment {
    private String apptNumber;
    private String patientName;
    private String address;
    private String contactNumber;
    private String dentistName;
    private String treatmentType;
    private String date;
    private String time;
    private double totalCost;

    public Appointment(String apptNumber, String patientName, String address, String contact, 
                       String dentist, String treatment, String date, String time, double totalCost) {
        this.apptNumber = apptNumber;
        this.patientName = patientName;
        this.address = address;
        this.contactNumber = contact;
        this.dentistName = dentist;
        this.treatmentType = treatment;
        this.date = date;
        this.time = time;
        this.totalCost = totalCost;
    }

    // Getters for Data Retrieval
    public String getApptNumber() { return apptNumber; }
    public String getPatientName() { return patientName; }
    public String getAddress() { return address; }
    public String getContactNumber() { return contactNumber; }
    public String getDentistName() { return dentistName; }
    public String getTreatmentType() { return treatmentType; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public double getTotalCost() { return totalCost; }
}
