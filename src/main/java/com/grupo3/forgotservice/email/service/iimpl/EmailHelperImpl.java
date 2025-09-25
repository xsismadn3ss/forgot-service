package com.grupo3.forgotservice.email.service.iimpl;

import com.grupo3.forgotservice.email.service.IEmailHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailHelperImpl implements IEmailHelper {
    private final JavaMailSender mailSender;

    public EmailHelperImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
            log.info("Email enviado correctamente");
        } catch (MessagingException e) {
            log.error("Error al enviar el email");
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
