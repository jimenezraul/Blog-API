package com.raul.blogapi.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public EmailService sendVerificationEmail(String email, String token) throws Exception {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);


            helper.setTo(email);
            helper.setSubject("Verify your email");
            String htmlContent = "<html><body><h1>Verify your email</h1><p>Click on the link below to verify your email</p><a href='http://localhost:8080/api/v1/auth/verify-email/" + token + "'>Verify</a></body></html>";
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}
