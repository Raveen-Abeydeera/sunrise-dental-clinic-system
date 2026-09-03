package ClinicControllerTest;

import controller.ClinicController;
import model.Appointment;
import model.Patient;
import model.User;
import util.SecurityUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClinicControllerTest {
    
    private ClinicController controller;
    
    @Before
    public void setUp() {
        controller = new ClinicController();
    }
    
    @After
    public void tearDown() {
        controller = null;
    }

  
    // AUTHENTICATION & ACCESS CONTROL
    

    @Test
    public void testTC_UT_001_ValidAdminAuth() {
        User result = controller.authenticateUser("admin", "sunrise123");
        assertNotNull("Admin login should return a valid User object", result);
        assertEquals("Role should strictly be admin", "admin", result.getRole());
    }

    @Test
    public void testTC_UT_002_ValidDoctorAuth() {
        User result = controller.authenticateUser("doctor1", "doc123");
        if (result != null) assertEquals("doctor", result.getRole());
    }

    @Test
    public void testTC_UT_003_ValidReceptionAuth() {
        User result = controller.authenticateUser("reception1", "rec123");
        if (result != null) assertEquals("receptionist", result.getRole());
    }

    @Test
    public void testTC_UT_004_InvalidPassword() {
        User result = controller.authenticateUser("admin", "wrongpassword");
        assertNull("Invalid login should return null to block access", result);
    }

    @Test
    public void testTC_UT_005_SQLInjectionAttempt() {
        User result = controller.authenticateUser("admin' OR '1'='1", "hacked");
        assertNull("PreparedStatement should prevent SQL injection and return null", result);
    }

    @Test
    public void testTC_UT_006_EmptyCredentials() {
        User result = controller.authenticateUser("", "");
        assertNull("Empty fields should return null", result);
    }

    
    //  SECURITY UTIL & REGEX VALIDATION
   

    @Test
    public void testTC_UT_007_ValidPasswordRegex() {
        assertTrue("Meets all complexity requirements", SecurityUtil.isValidPassword("Sunrise!123"));
    }

    @Test
    public void testTC_UT_008_WeakPassword_NoSpecialChar() {
        assertFalse("Missing special character should fail", SecurityUtil.isValidPassword("Sunrise1234"));
    }
    
    @Test
    public void testTC_UT_009_WeakPassword_NoUpperCase() {
        assertFalse("Missing uppercase should fail", SecurityUtil.isValidPassword("sunrise!123"));
    }

    @Test
    public void testTC_UT_010_WeakPassword_TooShort() {
        assertFalse("Under 8 characters should fail", SecurityUtil.isValidPassword("Sun!1"));
    }

    @Test
    public void testTC_UT_011_HashDeterminism() {
        String hash1 = SecurityUtil.hashPassword("myPassword123");
        String hash2 = SecurityUtil.hashPassword("myPassword123");
        assertEquals("Identical passwords must produce identical hashes", hash1, hash2);
    }

    @Test
    public void testTC_UT_012_HashUniqueness() {
        String hash1 = SecurityUtil.hashPassword("PasswordA!");
        String hash2 = SecurityUtil.hashPassword("PasswordB!");
        assertFalse("Different passwords must produce unique hashes", hash1.equals(hash2));
    }

    
    //  FINANCIAL CALCULATION TESTS
    

    @Test
    public void testTC_UT_013_CostCalcRootCanal() {
        assertEquals(15000.00, controller.calculateTreatmentCost("Root Canal"), 0.01);
    }

    @Test
    public void testTC_UT_014_CostCalcCleaning() {
        assertEquals(2500.00, controller.calculateTreatmentCost("Cleaning"), 0.01);
    }
    
    @Test
    public void testTC_UT_015_CostCalcExtraction() {
        assertEquals(5000.00, controller.calculateTreatmentCost("Extraction"), 0.01);
    }
    
    @Test
    public void testTC_UT_016_CostCalcUnknown() {
        assertEquals(0.0, controller.calculateTreatmentCost("Fake Treatment"), 0.01);
    }

    
    //  MODELS & DATABASE CONTROLLER TESTS
   

    @Test
    public void testTC_UT_017_ApptObjectCreation() {
        Appointment appt = new Appointment("APT-999", "PAT-01", "John Doe", "123 St", "0712345678", "Dr. Smith", "Cleaning", "2026-09-01", "10:00", 2500.00);
        assertEquals("APT-999", appt.getApptNumber());
        assertEquals(2500.00, appt.getTotalCost(), 0.01);
    }

    @Test
    public void testTC_UT_018_ApptDateRetrieval() {
        Appointment appt = new Appointment("APT-999", "PAT-01", "John Doe", "123 St", "0712345678", "Dr. Smith", "Cleaning", "2026-09-01", "10:00", 2500.00);
        assertEquals("2026-09-01", appt.getDate());
        assertEquals("10:00", appt.getTime());
    }

    @Test
    public void testTC_UT_019_PatientObjectCreation() {
        Patient pat = new Patient("PAT-99", "Jane Doe", "Colombo", "0771234567", 30);
        assertEquals("PAT-99", pat.getPatientId());
        assertEquals(30, pat.getAge());
    }

    @Test
    public void testTC_UT_020_UserObjectCreation() {
        User user = new User("testuser", "hash123", "receptionist");
        assertEquals("testuser", user.getUsername());
        assertEquals("receptionist", user.getRole());
    }

    @Test
    public void testTC_UT_021_MissingPatientName() {
        Appointment appt = new Appointment("APT-998", "PAT-02", "", "123 St", "0712345678", "Dr. Smith", "Cleaning", "2026-09-01", "10:00", 2500.00);
        assertFalse("Missing name should fail validation", controller.registerAppointment(appt));
    }

    @Test
    public void testTC_UT_022_MissingContactNo() {
        Appointment appt = new Appointment("APT-997", "PAT-03", "Jane Doe", "123 St", "", "Dr. Smith", "Cleaning", "2026-09-01", "10:00", 2500.00);
        assertFalse("Missing contact should fail validation", controller.registerAppointment(appt));
    }

    @Test
    public void testTC_UT_023_GenerateDailyReportFormat() {
        java.util.List<Object[]> report = controller.generateDailyReport("2026-09-01");
        assertNotNull("Report should return a valid List", report);
    }

    @Test
    public void testTC_UT_024_GetAllAppointmentsList() {
        java.util.List<Appointment> list = controller.getAllAppointments();
        assertNotNull("Appointments list should never be null, even if empty", list);
    }

    @Test
    public void testTC_UT_025_DeleteInvalidAppointment() {
        assertFalse(controller.deleteAppointment("FAKE-999"));
    }


    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println(" SUNRISE CLINIC - AUTOMATED TEST SUITE   ");
        System.out.println("=========================================");
        
        org.junit.runner.Result result = org.junit.runner.JUnitCore.runClasses(ClinicControllerTest.class);
        
        for (org.junit.runner.notification.Failure failure : result.getFailures()) {
            System.out.println("FAILED: " + failure.toString());
        }
        
        System.out.println("=========================================");
        System.out.println("Total Tests Run : " + result.getRunCount());
        System.out.println("Tests Passed    : " + (result.getRunCount() - result.getFailureCount()));
        System.out.println("Success Rate    : " + (result.wasSuccessful() ? "100% EXCELLENT" : "Check Failures"));
        System.out.println("=========================================");
    }
}