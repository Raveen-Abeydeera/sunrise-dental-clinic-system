
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSearch)
                .addGap(18, 18, 18)
                .addComponent(btnPrint)
                .addGap(132, 132, 132))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchNo, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSearchNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrint)
                    .addComponent(btnSearch))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
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
            if (!txtDisplayArea.getText().isEmpty() && !txtDisplayArea.getText().contains("Record not found")) {
                txtDisplayArea.print();
            } else {
                JOptionPane.showMessageDialog(this, "Search for a valid record first.");
            }
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Printing Error: " + ex.getMessage());
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
