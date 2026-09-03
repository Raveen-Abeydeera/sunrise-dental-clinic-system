
package view;

import controller.ClinicController;
import model.Appointment;
import javax.swing.JOptionPane;
import java.awt.print.PrinterException;

public class SearchBillingForm extends javax.swing.JFrame {
    
    private ClinicController controller;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SearchBillingForm.class.getName());

    public SearchBillingForm() {
        initComponents();
        controller = new ClinicController();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window on your monitor
        this.setResizable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtSearchNo = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDisplayArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Enter Appt No:");

        txtSearchNo.addActionListener(this::txtSearchNoActionPerformed);

        btnSearch.setText("Search");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        btnPrint.setText("Print Bill");
        btnPrint.addActionListener(this::btnPrintActionPerformed);

        txtDisplayArea.setEditable(false);
        txtDisplayArea.setColumns(20);
        txtDisplayArea.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txtDisplayArea.setRows(5);
        jScrollPane1.setViewportView(txtDisplayArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSearch)
                        .addGap(56, 56, 56)
                        .addComponent(btnPrint))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchNo, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addGap(208, 208, 208))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSearchNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch)
                    .addComponent(btnPrint))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchNoActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        Appointment appt = controller.searchAppointment(txtSearchNo.getText());

        if (appt != null) {
            txtDisplayArea.setText("=== SUNRISE DENTAL CLINIC BILL ===\n");
            txtDisplayArea.append("Appt No:      " + appt.getApptNumber() + "\n");
            txtDisplayArea.append("Patient Name: " + appt.getPatientName() + "\n");
            txtDisplayArea.append("Dentist:      " + appt.getDentistName() + "\n");
            txtDisplayArea.append("Treatment:    " + appt.getTreatmentType() + "\n");
            txtDisplayArea.append("----------------------------------\n");
            txtDisplayArea.append("TOTAL COST:   Rs. " + appt.getTotalCost() + "\n");
            txtDisplayArea.append("==================================\n");
        } else {
            txtDisplayArea.setText("Record not found.");
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:
        try {
            if (txtDisplayArea.getText().isEmpty() || txtDisplayArea.getText().contains("Record not found")) {
                javax.swing.JOptionPane.showMessageDialog(this, "Search for a valid record first.");
                return;
            }

            // 1. Generate an HTML file locally
            java.io.File htmlFile = new java.io.File("Sunrise_Clinic_Receipt.html");
            java.io.FileWriter writer = new java.io.FileWriter(htmlFile);
            
            writer.write("<html><head><title>Sunrise Dental - Receipt</title></head>");
            writer.write("<body style='font-family: Arial, sans-serif; padding: 40px; text-align: center; background-color: #f4f4f9;'>");
            writer.write("<div style='background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0px 0px 10px #ccc; display: inline-block; text-align: left;'>");
            writer.write("<h1 style='color: #2c3e50;'>Sunrise Dental Clinic</h1>");
            writer.write("<h3 style='color: #7f8c8d;'>Official Patient Receipt</h3><hr>");
            
            // Format the Java text area content for the web
            String webContent = txtDisplayArea.getText().replace("\n", "<br>").replace(" ", "&nbsp;");
            writer.write("<p style='font-family: monospace; font-size: 16px;'>" + webContent + "</p>");
            
            writer.write("<hr><br>");
            writer.write("<button onclick='window.print()' style='padding: 10px 20px; background-color: #3498db; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 16px;'>Print / Save as PDF</button>");
            writer.write("</div></body></html>");
            writer.close();

            // 2. Instruct the OS to open the file in the default browser (Chrome)
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().browse(htmlFile.toURI());
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Browser popup not supported on this OS.");
            }

        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error generating receipt: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new SearchBillingForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtDisplayArea;
    private javax.swing.JTextField txtSearchNo;
    // End of variables declaration//GEN-END:variables
}
