package com.chmsu.heis.services;

import com.chmsu.heis.model.document.Document;
import com.chmsu.heis.model.document.DocumentGroup;
import com.chmsu.heis.model.document.DocumentMultiple;
import com.chmsu.heis.model.document.Email;
import com.chmsu.heis.repository.*;
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

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentGroupRepository documentGroupRepository;
    @Autowired
    private DocumentMultipleRepository documentMultipleRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private LogsRepository logsRepository;

    @Value("${spring.mail.username}")
    private String from;
    //Service Method for getting the next Document Number
    public Integer getNextDocumentNumber(){
       Integer currentDocumentNumber =  this.documentRepository.getNextDocumentNumber();
       Integer nextDocumentNumber = currentDocumentNumber + 1;
        return nextDocumentNumber;
    }

    //Service Method for Sending Document to Individual Receiver
    public Document sendIndividualDocument(Document document) {
        ObjectMapper objectMapper = new ObjectMapper();
        String ccJson;
        try {
            ccJson = objectMapper.writeValueAsString(document.getCc());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Handle the error appropriately
            throw new RuntimeException("Failed to convert cc list to JSON", e);
        }

        documentRepository.insertDocument(
                document.getDocumentNumber(),
                document.getSubject(),
                document.getDateOfLetter(),
                document.getType(),
                document.getAttention(),
                document.getThrough(),
                document.getFrom(),
                document.getPageCount(),
                document.getAttachment(),
                document.getCampus(),
                document.getDepartmentId(),
                ccJson, // Use the JSON string here
                document.getActionDate(),
                document.getStatus(),
                document.getDateFinished(),
                document.getEncoder(),
                document.getTimestamp()
        );
        System.out.println(document.getDocumentNumber() + " From Service");
        return document;
    }

    public void sendGroupDocument(DocumentGroup email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        String htmlContent = "<html><body>" +
                "<h2>Records Management System</h2>" +
                "<pre>" + email.getMessage() + "</pre>" +  // Preserve formatting with <pre> tag
                "</body></html>";

        // Set basic email attributes
        helper.setFrom(from);

        helper.setSubject(email.getSubject());
        helper.setText(htmlContent,true);

        // Process CC addresses
        ObjectMapper objectMapper = new ObjectMapper();
        String ccJson;
        String attentionJson;
        try {
            ccJson = objectMapper.writeValueAsString(email.getCc());
            attentionJson = objectMapper.writeValueAsString(email.getAttention());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert CC list to JSON", e);
        }

        String[] ccArray;
        String[] attentionArray;
        try {
            ccArray = objectMapper.readValue(ccJson, String[].class);
            attentionArray = objectMapper.readValue(attentionJson, String[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert JSON to CC array", e);
        }
        helper.setTo(attentionArray);
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

        // Save the email details to the repository
        String formattedDate = formatDate(email.getDateOfLetter());
//        emailRepository.saveEmail(
//                email.getDocumentNumber(),
//                email.getSubject(),
//                formattedDate,
//                email.getType(),
//               attentionJson,
//                email.getThrough(),
//                email.getFrom(),
//                email.getNumberOfPages(),
//                email.getAttachment(),
//                email.getCampus(),
//                email.getDepartment(),
//                ccJson,
//                email.getEncoder(),
//                email.getMessage());
//        logsRepository.insertLogs(email.getEncoder(),email.getMessage(),email.getDateOfLetter());
    }

    private String formatDate(java.sql.Date dateOfLetter) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(dateOfLetter);
    }

    public void sendMultiple(DocumentGroup email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        String htmlContent = "<html><body>" +
                "<h2>Records Management System</h2>" +
                "<pre>" + email.getMessage() + "</pre>" +  // Preserve formatting with <pre> tag
                "</body></html>";

        // Set basic email attributes
        helper.setFrom(from);

        helper.setSubject(email.getSubject());
        helper.setText(htmlContent,true);

        // Process CC addresses
        ObjectMapper objectMapper = new ObjectMapper();
        String ccJson;
        String attentionJson;
        try {
            ccJson = objectMapper.writeValueAsString(email.getCc());
            attentionJson = objectMapper.writeValueAsString(email.getAttention());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert CC list to JSON", e);
        }

        String[] ccArray;
        String[] attentionArray;
        try {
            ccArray = objectMapper.readValue(ccJson, String[].class);
            attentionArray = objectMapper.readValue(attentionJson, String[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert JSON to CC array", e);
        }
        helper.setTo(attentionArray);
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

        // Save the email details to the repository
//        String formattedDate = formatDate(email.getDateOfLetter());
//        emailRepository.saveEmail(
//                email.getDocumentNumber(),
//                email.getSubject(),
//                formattedDate,
//                email.getType(),
//                attentionJson,
//                email.getThrough(),
//                email.getFrom(),
//                email.getNumberOfPages(),
//                email.getAttachment(),
//                email.getCampus(),
//                email.getDepartment(),
//                ccJson,
//                email.getEncoder(),
//                email.getMessage());
//        logsRepository.insertLogs(email.getEncoder(),email.getMessage(),email.getDateOfLetter());
    }


}
