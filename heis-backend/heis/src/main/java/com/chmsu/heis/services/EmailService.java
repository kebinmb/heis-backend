package com.chmsu.heis.services;

import com.chmsu.heis.model.document.Email;
import com.chmsu.heis.repository.EmailRepository;
import com.chmsu.heis.repository.LogsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private LogsRepository logsRepository;

    @Value("${spring.mail.username}")
    private String from;

    public void sendEmail(Email email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        String htmlContent = "<html><body>" +
                "<h2>Records Management System</h2>" +
                "<pre>" + email.getMessage() + "</pre>" +  // Preserve formatting with <pre> tag
                "</body></html>";

        // Set basic email attributes
        helper.setFrom(from);
        helper.setTo(email.getAttention());
        helper.setSubject(email.getSubject());
        helper.setText(htmlContent,true);

        // Process CC addresses
        ObjectMapper objectMapper = new ObjectMapper();
        String ccJson;
        try {
            ccJson = objectMapper.writeValueAsString(email.getCc());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert CC list to JSON", e);
        }

        String[] ccArray;
        try {
            ccArray = objectMapper.readValue(ccJson, String[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert JSON to CC array", e);
        }

        helper.setCc(ccArray);

        // Attach the file if present
        if (email.getAttachment() != null && !email.getAttachment().isEmpty()) {
            File file = new File(email.getAttachment());
            if (file.exists() && file.isFile()) {
                FileSystemResource fileResource = new FileSystemResource(file);
                helper.addAttachment(fileResource.getFilename(), fileResource);
            } else {
                System.out.println("Attachment file is not found");
            }
        }

        // Send the email
        mailSender.send(mimeMessage);

        Integer userIdAttention = emailRepository.getUserId(email.getAttention());
        // Save the email details to the repository
        //CHANGE THE EMAIL TO INTEGER
        String formattedDate = formatDate(email.getDateOfLetter());
        emailRepository.saveEmail(
                email.getDocumentNumber(),
                email.getSubject(),
                formattedDate,
                email.getType(),
                userIdAttention,
                email.getThrough(),
                email.getFrom(),
                email.getPageCount(),
                email.getAttachment(),
                email.getCampus(),
                email.getDepartmentId(),
                ccJson,
                email.getEncoder(),
                email.getMessage());
        logsRepository.insertLogs(email.getEncoder(),email.getMessage(),email.getDateOfLetter());
    }

    private String formatDate(java.sql.Date dateOfLetter) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(dateOfLetter);
    }

    public Integer documentNumberCount() {
        return emailRepository.documentNumber();
    }

    public String getEmail(String name){
        return emailRepository.getEmail(name);
    }
    public Email getEmailById(Long id) {
        return emailRepository.findById(id).orElse(null);
    }

    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }
}
