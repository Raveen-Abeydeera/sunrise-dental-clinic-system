package util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

   
    private static final String CLINIC_EMAIL = "yourclinicemail@gmail.com";
   
    private static final String CLINIC_PASSWORD = "your-16-digit-app-password"; 

    public static void sendAppointmentConfirmation(String toEmail, String patientName, String apptNo, String date, String time, double cost) {
        
        
        new Thread(() -> {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(CLINIC_EMAIL, CLINIC_PASSWORD);
                        }
                    });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(CLINIC_EMAIL));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                message.setSubject("Appointment Confirmation - Sunrise Dental Clinic");

                String emailBody = "Dear " + patientName + ",\n\n"
                        + "Your appointment has been successfully booked.\n\n"
                        + "Appointment No: " + apptNo + "\n"
                        + "Date: " + date + "\n"
                        + "Time: " + time + "\n"
                        + "Estimated Cost: Rs. " + cost + "\n\n"
                        + "Thank you for choosing Sunrise Dental Clinic!";

                message.setText(emailBody);
                Transport.send(message);
                
                System.out.println("Confirmation email sent successfully to " + toEmail);

            } catch (MessagingException e) {
                System.out.println("Failed to send email: " + e.getMessage());
            }
        }).start();
    }
}