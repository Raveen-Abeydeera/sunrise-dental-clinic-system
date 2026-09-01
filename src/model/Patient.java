package model;

public class Patient {
    private String patientId;
    private String fullName;
    private String address;
    private String contactNumber;
    private int age;

    public Patient(String patientId, String fullName, String address, String contactNumber, int age) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.address = address;
        this.contactNumber = contactNumber;
        this.age = age;
    }

    public String getPatientId() { return patientId; }
    public String getFullName() { return fullName; }
    public String getAddress() { return address; }
    public String getContactNumber() { return contactNumber; }
    public int getAge() { return age; }
}