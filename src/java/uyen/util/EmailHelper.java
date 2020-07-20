/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.util;

import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author HP
 */
public class EmailHelper implements Serializable {

    private String toEmail;
    private final String ADMIN_EMAIL = "j3lp0009@gmail.com";
    private final String ADMIN_PSW = "Abc1235713";
    //  private final String HOST = "localhost";

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public EmailHelper() {
    }

    public void sendEmail(String code) {

        Properties properties = System.getProperties();
        //properties.setProperty("mail.smtp.host", HOST);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ADMIN_EMAIL, ADMIN_PSW);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ADMIN_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("ACTIVATION EMAIL");
            message.setText("Your activation code: " + code);

            Transport.send(message);
        } catch (MessagingException ex) {

        }
    }

    public static String getRandomActivationCode() {
        return UUID.randomUUID().toString();
    }
}
