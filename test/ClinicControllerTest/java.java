
package ClinicControllerTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author User
 */
public class ClinicControllerTest {
    
    private ClinicController controller;
    
    @Before
    public void setUp() {
        // Initializes a fresh controller before every test
        controller = new ClinicController();
    }
    
    @After
    public void tearDown() {
        controller = null;
    }

    @Test
    public void testAuthenticateUser_ValidStaff() {
        System.out.println("Testing Authentication: Valid Credentials");
        boolean result = controller.authenticateUser("admin", "sunrise123");
        assertTrue("Admin login should return true", result);
    }

    @Test
    public void testAuthenticateUser_InvalidStaff() {
        System.out.println("Testing Authentication: Invalid Credentials");
        boolean result = controller.authenticateUser("hacker", "wrongpassword");
        assertFalse("Invalid login should return false", result);
    }

    @Test
    public void testCalculateTreatmentCost_RootCanal() {
        System.out.println("Testing Bill Calculation: Root Canal");
        double expectedCost = 15000.00;
        double actualCost = controller.calculateTreatmentCost("Root Canal");
        assertEquals("Root Canal should cost 15000.00", expectedCost, actualCost, 0.01);
    }
    
    @Test
    public void testCalculateTreatmentCost_InvalidTreatment() {
        System.out.println("Testing Bill Calculation: Unknown Treatment");
        double expectedCost = 0.0;
        double actualCost = controller.calculateTreatmentCost("Fake Treatment");
        assertEquals("Unknown treatment should default to 0.0", expectedCost, actualCost, 0.01);
    }
}
