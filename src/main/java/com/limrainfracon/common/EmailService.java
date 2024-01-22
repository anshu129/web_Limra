package com.limrainfracon.common;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.limrainfracon.model.User;
import com.limrainfracon.request.EmailRequest;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendEmail(User user) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("limra.admin@limrainfracon.com");
            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to Our Service");

            Context context = new Context();
            context.setVariable("user", user);

            String html = templateEngine.process("email-template", context);

            helper.setText(html, true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendEmailToUs(EmailRequest emailReq) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo("limra.admin@limrainfracon.com");
            helper.setSubject(emailReq.getEmailSubject());
            helper.setFrom(emailReq.getEmail());
            //helper.setText(emailReq.getEmailBody());

            Context context = new Context();
            context.setVariable("emailReq", emailReq);

            String html = templateEngine.process("concern-email-temp", context);

            helper.setText(html, true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
