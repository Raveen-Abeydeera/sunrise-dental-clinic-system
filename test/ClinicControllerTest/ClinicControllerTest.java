package ClinicControllerTest;

// 1. ADDED MISSING IMPORTS
import controller.ClinicController;
import model.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClinicControllerTest {
    
    private ClinicController controller;
    
    @BeforeEach
    public void setUp() {
        // Initializes a fresh controller before every test
        controller = new ClinicController();
    }
    
    @AfterEach
    public void tearDown() {
        controller = null;
    }

    @Test
    public void testAuthenticateUser_ValidStaff() {
        System.out.println("Testing Authentication: Valid Credentials");
        
        // 2. UPDATED TO EXPECT A 'User' OBJECT INSTEAD OF A BOOLEAN
        User result = controller.authenticateUser("admin", "sunrise123");
        
        // 3. UPDATED JUNIT 5 ASSERTION SYNTAX
        assertNotNull(result, "Admin login should return a valid User object");
        assertEquals("admin", result.getRole(), "Role should strictly be admin");
    }

    @Test
    public void testAuthenticateUser_InvalidStaff() {
        System.out.println("Testing Authentication: Invalid Credentials");
        
        // 2. UPDATED TO EXPECT A 'User' OBJECT
        User result = controller.authenticateUser("hacker", "wrongpassword");
        
        // 3. EXPECTING NULL FOR FAILED LOGINS
        assertNull(result, "Invalid login should return null to block access");
    }

    @Test
    public void testCalculateTreatmentCost_RootCanal() {
        System.out.println("Testing Bill Calculation: Root Canal");
        double expectedCost = 15000.00;
        double actualCost = controller.calculateTreatmentCost("Root Canal");
        assertEquals(expectedCost, actualCost, 0.01, "Root Canal should cost 15000.00");
    }
    
    @Test
    public void testCalculateTreatmentCost_InvalidTreatment() {
        System.out.println("Testing Bill Calculation: Unknown Treatment");
        double expectedCost = 0.0;
        double actualCost = controller.calculateTreatmentCost("Fake Treatment");
        assertEquals(expectedCost, actualCost, 0.01, "Unknown treatment should default to 0.0");
    }
}