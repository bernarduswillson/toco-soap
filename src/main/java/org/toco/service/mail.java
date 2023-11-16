package org.toco.service;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class mail {
    public static void sendMail(String email, String body) {
        // Sender's email address and password
        final String username = "tocowbd@gmail.com";
        final String password = "drwf pahx wnwr ahnw";

        // Recipient's email address
        String to = email;

        // SMTP server settings
        String host = "smtp.gmail.com";
        String port = "587";

        // Set the properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Get the Session object
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress(username));

            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Thankyou for your purchase with toco!");

            // Now set the actual message
            message.setContent(
                    "<h1>Thank You for Your Purchase!</h1>\n" +
                            "    <p>We appreciate your recent purchase of our merchandise. Your support means the world to us!</p>\n"
                            +
                            "    <p>" + body + "</p>\n" +
                            "    <p>If you have any questions or concerns regarding your order, feel free to reply to this email and we'll be in contact ASAP.</p>\n"
                            +
                            "    <p>Thank you again for choosing toco to be your partner in studying language!</p>\n" +
                            "    <p>Best regards,</p>\n" +
                            "    <p>Toco Team</p>\n" +
                            "    <a href=\"http://localhost:8008\" class=\"button\">Visit Our Website</a>",
                    "text/html");

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
