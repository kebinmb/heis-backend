package com.chmsu.heis.services;

import com.chmsu.heis.model.document.Email;
import com.chmsu.heis.repository.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Value("${spring.mail.username}")
    private String from;

    public void sendEmail(Email email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        mimeMessage.setContent(email.getMessage(), "text/html; charset=UTF-8");

        helper.setFrom(from);
        helper.setTo(email.getAttention());
        helper.setCc(email.getCc());
        helper.setSubject(email.getSubject());
        helper.setText(email.getMessage(), true);
        if (email.getAttachment() != null) {
            FileSystemResource file = new FileSystemResource(new File(email.getAttachment()));
            helper.addAttachment("Attachment", file);
        }
        else{
            email.setAttachment(null);
        }
        mailSender.send(mimeMessage);
        emailRepository.saveEmail(
                email.getDocumentNumber(),
                email.getSubject(),
                email.getDateOfLetter(),
                email.getType(),
                email.getAttention(),
                email.getThrough(),
                email.getFrom(),
                email.getPageCount(),
                email.getAttachment(),email.getCampus(),
                email.getDepartmentId(),
                email.getCc(),
                email.getEncoder());
    }

    public Email getEmailById(Long id) {
        return emailRepository.findById(id).orElse(null);
    }

    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }
}
