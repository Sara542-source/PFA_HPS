package com.hps.transaction_monitor;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailTest {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailTest(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @PostConstruct

    public void sendTestEmail() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("selimanisara2@gmail.com");
        mailMessage.setSubject("Test Email from Transaction Monitor");
        mailMessage.setText("This is a test email sent from Transaction Monitor at startup.");
        try {
            mailSender.send(mailMessage);
            System.out.println("Test email sent successfully!");
        } catch (Exception e) {
            System.err.println("Failed to send test email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
