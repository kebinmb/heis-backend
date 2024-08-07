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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void sendGroupDocument(DocumentGroup email) throws MessagingException, JsonProcessingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        String htmlContent = "<html><body>" +
                "<h2>Records Management System</h2>" +
                "<pre>" + email.getMessage() + "</pre>" +  // Preserve formatting with <pre> tag
                "</body></html>";

        // Set basic email attributes
        helper.setFrom(from);
        helper.setSubject(email.getSubject());
        helper.setText(htmlContent, true);

        // Set To and CC addresses
        helper.setTo(email.getAttention().toArray(new String[0]));
        helper.setCc(email.getCc().toArray(new String[0]));
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> attachmentFilenames = new ArrayList<>();
        if (email.getAttachment() != null && !email.getAttachment().isEmpty()) {
            for (String attachmentPath : email.getAttachment()) {
                File file = new File(attachmentPath);
                if (file.exists() && file.isFile()) {
                    FileSystemResource fileResource = new FileSystemResource(file);
                    helper.addAttachment(fileResource.getFilename(), fileResource);
                    // Extract the filename
                    String fileName = file.getName();
                    attachmentFilenames.add(fileName);
                } else {
                    System.out.println("Attachment file not found: " + attachmentPath);
                }
            }
        }

        // Send the email
        mailSender.send(mimeMessage);

        // Convert attention addresses to Integer user IDs
        List<String> attentionArrayNumber = new ArrayList<>();
        for (String attention : email.getAttention()) {
            String attentionId = documentGroupRepository.getUserId(attention);
            if (attentionId != null) {
                attentionArrayNumber.add(attentionId);
            } else {
                System.out.println("User ID not found for attention: " + attention);
            }
        }

        // Convert other fields to Integer user IDs
        String userIdThrough = documentGroupRepository.getUserId(email.getThrough());
        String userIdFrom = documentGroupRepository.getUserId(email.getFrom());
        String attachmentJson = objectMapper.writeValueAsString(attachmentFilenames);
        // Convert attentionArrayNumber to JSON
        String attentionJson;
        String ccJson;
        try {
            attentionJson = objectMapper.writeValueAsString(attentionArrayNumber);
            ccJson = objectMapper.writeValueAsString(email.getCc());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert attentionArrayNumber or CC list to JSON", e);
        }
        String attention = convertAttentionListToJsonOrDelimitedString(attentionArrayNumber);

        // Save the email details to the repository
        String formattedDate = formatDate(email.getDateOfLetter());
        emailRepository.saveEmailGroup(
                email.getDocumentNumber(),
                email.getSubject(),
                formattedDate,
                email.getType(),
                attention, // Save attention JSON
                userIdThrough,
                userIdFrom,
                email.getNumberOfPages(),
                attachmentJson,
                email.getCampus(),
                email.getDepartment(),
                ccJson,
                email.getEncoder(),
                email.getMessage()
        );

        // Log the email details
        logsRepository.insertLogs(email.getEncoder().longValue(), email.getMessage(), email.getDateOfLetter());
    }

    private String convertAttentionListToJsonOrDelimitedString(List<String> attentionList) {
        // Convert list to JSON array or delimited string, depending on chosen approach
        // Example implementation:
        // return new ObjectMapper().writeValueAsString(attentionList);
        return String.join(",", attentionList);
    }




    private String formatDate(java.sql.Date dateOfLetter) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(dateOfLetter);
    }

    public void sendMultiple(DocumentGroup email) throws MessagingException, JsonProcessingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        String htmlContent = "<html><body>" +
                "<h2>Records Management System</h2>" +
                "<pre>" + email.getMessage() + "</pre>" +  // Preserve formatting with <pre> tag
                "</body></html>";

        // Set basic email attributes
        helper.setFrom(from);
        helper.setSubject(email.getSubject());
        helper.setText(htmlContent, true);

        // Set To and CC addresses
        helper.setTo(email.getAttention().toArray(new String[0]));
        helper.setCc(email.getCc().toArray(new String[0]));
        ObjectMapper objectMapper = new ObjectMapper();
        // Attach the file if present
        List<String> attachmentFilenames = new ArrayList<>();
        if (email.getAttachment() != null && !email.getAttachment().isEmpty()) {
            for (String attachmentPath : email.getAttachment()) {
                File file = new File(attachmentPath);
                if (file.exists() && file.isFile()) {
                    FileSystemResource fileResource = new FileSystemResource(file);
                    helper.addAttachment(fileResource.getFilename(), fileResource);
                    // Extract the filename
                    String fileName = file.getName();
                    attachmentFilenames.add(fileName);
                } else {
                    System.out.println("Attachment file not found: " + attachmentPath);
                }
            }
        }

        String attachmentJson = objectMapper.writeValueAsString(attachmentFilenames);

        // Send the email
        mailSender.send(mimeMessage);

        // Convert attention addresses to Integer user IDs
        List<String> attentionArrayNumber = new ArrayList<>();
        for (String attention : email.getAttention()) {
            String attentionId = documentGroupRepository.getUserId(attention);
            if (attentionId != null) {
                attentionArrayNumber.add(attentionId);
            } else {
                System.out.println("User ID not found for attention: " + attention);
            }
        }

        // Convert other fields to Integer user IDs
        String userIdThrough = documentGroupRepository.getUserId(email.getThrough());
        String userIdFrom = documentGroupRepository.getUserId(email.getFrom());

        // Convert attentionArrayNumber to JSON

        String attentionJson;
        String ccJson;
        try {
            attentionJson = objectMapper.writeValueAsString(email.getAttention());
            ccJson = objectMapper.writeValueAsString(email.getCc());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert attentionArrayNumber or CC list to JSON", e);
        }

        // Save the email details to the repository
        String formattedDate = formatDate(email.getDateOfLetter());
        emailRepository.saveEmailGroup(
                email.getDocumentNumber(),
                email.getSubject(),
                formattedDate,
                email.getType(),
                attentionJson, // Save attention JSON as VARCHAR
                userIdThrough,
                userIdFrom,
                email.getNumberOfPages(),
               attachmentJson,
                email.getCampus(),
                email.getDepartment(),
                ccJson,
                email.getEncoder(),
                email.getMessage()
        );

        // Log the email details
        logsRepository.insertLogs(email.getEncoder().longValue(), email.getMessage(), email.getDateOfLetter());
    }

}
