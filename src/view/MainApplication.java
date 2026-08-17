package view;

import controller.ClinicController;
import model.Appointment;
import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;

public class MainApplication extends JFrame {
    
    private ClinicController controller;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MainApplication() {
        controller = new ClinicController();
        setTitle("Sunrise Dental Clinic - Patient Management System");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createDashboardPanel(), "Dashboard");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Sunrise Dental Secure Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        
        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(60, 141, 188));
        loginBtn.setForeground(Color.WHITE);

        loginBtn.addActionListener(e -> {
            if (controller.authenticateUser(userField.getText(), new String(passField.getPassword()))) {
                cardLayout.show(mainPanel, "Dashboard");
                userField.setText("");
                passField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; panel.add(titleLabel, gbc);
        gbc.gridwidth = 1; gbc.gridy = 1; panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; panel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; panel.add(passField, gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(loginBtn, gbc);

        return panel;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        tabbedPane.addTab("Register Appointment", createRegistrationTab());
        tabbedPane.addTab("Search & Billing", createSearchBillingTab());
        tabbedPane.addTab("Help System", createHelpTab());

        JMenuBar menuBar = new JMenuBar();
        JMenu systemMenu = new JMenu("System Options");
        JMenuItem logoutItem = new JMenuItem("Log Out");
        JMenuItem exitItem = new JMenuItem("Safely Exit Application");
        
        logoutItem.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
        exitItem.addActionListener(e -> System.exit(0));
        
        systemMenu.add(logoutItem);
        systemMenu.addSeparator();
        systemMenu.add(exitItem);
        menuBar.add(systemMenu);
        
        panel.add(menuBar, BorderLayout.NORTH);
        panel.add(tabbedPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createRegistrationTab() {
        JPanel panel = new JPanel(new GridLayout(9, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JTextField apptNo = new JTextField();
        JTextField patName = new JTextField();
        JTextField address = new JTextField();
        JTextField contact = new JTextField();
        JComboBox<String> dentists = new JComboBox<>(new String[]{"Dr. Smith", "Dr. Perera", "Dr. Silva"});
        JComboBox<String> treatments = new JComboBox<>(new String[]{"Cleaning", "Root Canal", "Extraction", "Whitening"});
        JTextField date = new JTextField("YYYY-MM-DD");
        JTextField time = new JTextField("HH:MM");
        JButton submitBtn = new JButton("Register & Save to Database");
        submitBtn.setBackground(new Color(40, 167, 69));
        submitBtn.setForeground(Color.WHITE);

        submitBtn.addActionListener(e -> {
            if (apptNo.getText().isEmpty() || patName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Appointment No and Name are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double cost = controller.calculateTreatmentCost(treatments.getSelectedItem().toString());
            Appointment newAppt = new Appointment(
                apptNo.getText(), patName.getText(), address.getText(), contact.getText(), 
                dentists.getSelectedItem().toString(), treatments.getSelectedItem().toString(), 
                date.getText(), time.getText(), cost
            );

            if (controller.registerAppointment(newAppt)) {
                JOptionPane.showMessageDialog(panel, "Success! Appointment Saved.\nTotal Estimated Cost: Rs. " + cost);
                apptNo.setText(""); patName.setText(""); address.setText(""); contact.setText("");
            } else {
                JOptionPane.showMessageDialog(panel, "Database Error. Could not save.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel("Unique Appointment No:")); panel.add(apptNo);
        panel.add(new JLabel("Patient Full Name:")); panel.add(patName);
        panel.add(new JLabel("Patient Address:")); panel.add(address);
        panel.add(new JLabel("Contact Number:")); panel.add(contact);
        panel.add(new JLabel("Assign Dentist:")); panel.add(dentists);
        panel.add(new JLabel("Treatment Type:")); panel.add(treatments);
        panel.add(new JLabel("Date (YYYY-MM-DD):")); panel.add(date);
        panel.add(new JLabel("Time (HH:MM):")); panel.add(time);
        panel.add(new JLabel("")); panel.add(submitBtn);

        return panel;
    }

    private JPanel createSearchBillingTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        JTextField searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search Database");
        JButton printBtn = new JButton("Print Bill / Receipt");
        
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setMargin(new Insets(10,10,10,10));

        topPanel.add(new JLabel("Enter Appt Number:"));
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        topPanel.add(printBtn);

        searchBtn.addActionListener(e -> {
            Appointment appt = controller.searchAppointment(searchField.getText());
            if (appt != null) {
                displayArea.setText("=====================================\n");
                displayArea.append("     SUNRISE DENTAL CLINIC BILL\n");
                displayArea.append("=====================================\n");
                displayArea.append("Appointment No: " + appt.getApptNumber() + "\n");
                displayArea.append("Patient Name:   " + appt.getPatientName() + "\n");
                displayArea.append("Date & Time:    " + appt.getDate() + " | " + appt.getTime() + "\n");
                displayArea.append("Attending:      " + appt.getDentistName() + "\n");
                displayArea.append("-------------------------------------\n");
                displayArea.append("Treatment:      " + appt.getTreatmentType() + "\n");
                displayArea.append("Total Cost:     Rs. " + appt.getTotalCost() + "\n");
                displayArea.append("=====================================\n");
                displayArea.append("Thank you for choosing Sunrise Dental!\n");
            } else {
                displayArea.setText("Record not found in the database.");
            }
        });

        printBtn.addActionListener(e -> {
            try {
                if(!displayArea.getText().isEmpty() && !displayArea.getText().contains("Record not found")) {
                    displayArea.print();
                } else {
                    JOptionPane.showMessageDialog(panel, "Please search for a valid appointment first.");
                }
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(panel, "Printer Error: " + ex.getMessage());
            }
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createHelpTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea helpText = new JTextArea();
        helpText.setText("\n   === SUNRISE DENTAL SYSTEM HELP GUIDE ===\n\n" +
                "   1. LOGIN: Enter your authorized credentials ('admin', 'sunrise123').\n" +
                "   2. REGISTER APPOINTMENT: Fill out all fields. Appointment No and Name are mandatory.\n" +
                "      The system will automatically calculate the bill based on the treatment selected.\n" +
                "   3. SEARCH & BILLING: Enter a previously saved Appointment Number.\n" +
                "      Click 'Search Database' to retrieve the record.\n" +
                "      Click 'Print Bill' to send the generated receipt to your local printer.\n" +
                "   4. EXIT: Use the 'System Options' menu at the top left to safely log out or close.");
        helpText.setEditable(false);
        helpText.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(new JScrollPane(helpText), BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainApplication().setVisible(true);
        });
    }
}
