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
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class EmailService {
    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private LogsRepository logsRepository;

    // Method to configure JavaMailSender dynamically
    private JavaMailSender getDynamicJavaMailSender(String host, int port, String username, String password) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }

    public void sendEmail(Email email, String host, int port, String username, String password) throws MessagingException {
        // Get a dynamically configured JavaMailSender
        JavaMailSender mailSender = getDynamicJavaMailSender(host, port, username, password);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        String htmlContent = "<html>" +
                "<head>" +
                "<title>Records Management System</title>" +
                "</head>" +
                "<body>" +
                "<header><h2>Records Management System</h2></header>" +
                "<pre>" + email.getMessage() + "</pre>" +
                "<footer>Footer</footer>" +
                "</body>" +
                "</html>";

        try {
            // Set basic email attributes
            helper.setFrom(username);


            List<String> toList = new ArrayList<>();
            toList.add(email.getAttention());
            toList.add(email.getThrough());
            helper.setTo(toList.toArray(new String[0]));

            helper.setSubject(email.getSubject());
            helper.setText(htmlContent, true);

            // Process CC addresses
            ObjectMapper objectMapper = new ObjectMapper();
            String ccJson = objectMapper.writeValueAsString(email.getCc());
            String[] ccArray = objectMapper.readValue(ccJson, String[].class);
            helper.setCc(ccArray);

            // Attach the file if present
            List<String> attachmentFilenames = new ArrayList<>();
            if (email.getAttachment() != null && !email.getAttachment().isEmpty()) {
                for (String attachmentPath : email.getAttachment()) {
                    File file = new File(attachmentPath);
                    if (file.exists() && file.isFile()) {
                        FileSystemResource fileResource = new FileSystemResource(file);
                        helper.addAttachment(fileResource.getFilename(), fileResource);
                        attachmentFilenames.add(file.getName());
                    } else {
                        System.out.println("Attachment file not found: " + attachmentPath);
                    }
                }
            }

            // Save the email details to the repository
            Integer userIdAttention = emailRepository.getUserId(email.getAttention());
            Integer userIdThrough = emailRepository.getUserId(email.getThrough());
            Integer userIdFrom = emailRepository.getUserId(email.getFrom());
            String attachmentJson = objectMapper.writeValueAsString(attachmentFilenames);
            String formattedDate = formatDate(email.getDateOfLetter());
            emailRepository.saveEmail(
                    email.getDocumentNumber(),
                    email.getSubject(),
                    formattedDate,
                    email.getType(),
                    userIdAttention,
                    userIdThrough,
                    userIdFrom,
                    email.getPageCount(),
                    attachmentJson,
                    email.getCampus(),
                    email.getDepartmentId(),
                    ccJson,
                    email.getEncoder(),
                    email.getMessage()
            );

            logsRepository.insertLogs(email.getEncoder().longValue(), "Processed document number: " + email.getDocumentNumber(), email.getDateOfLetter());

            // Send the email only after successful database insertion
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new MessagingException("Failed to send email", e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to process CC addresses", e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while sending the email or saving to the repository", e);
        }
    }

    private String formatDate(Date dateOfLetter) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(dateOfLetter);
    }

    public Integer documentNumberCount() {
        return emailRepository.documentNumber();
    }

    public String getEmail(String name) {
        return emailRepository.getEmail(name);
    }

    public Email getEmailById(Long id) {
        return emailRepository.findById(id).orElse(null);
    }

    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }
}
