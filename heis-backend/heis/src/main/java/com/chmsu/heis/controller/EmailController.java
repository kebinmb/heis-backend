package com.chmsu.heis.controller;

import com.chmsu.heis.model.document.DocumentGroup;
import com.chmsu.heis.model.document.Email;
import com.chmsu.heis.services.DocumentService;
import com.chmsu.heis.services.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/emails")
public class EmailController {

//    private static final String UPLOAD_DIR = "/Apache24/htdocs/heis/uploads";
    private static final String UPLOAD_DIR = "/Users/kevin/Downloads";

    @Autowired
    private EmailService emailService;

    @Autowired
    private DocumentService documentService;

    @Value("${email.host:default-host}")
    private String defaultHost; // Inject from properties with a default value

    @Value("${email.port:25}")
    private int defaultPort; // Inject from properties with a default value

    @Value("${email.username:default-username}")
    private String defaultUsername; // Inject from properties with a default value

    @Value("${email.password:default-password}")
    private String defaultPassword; // Inject from properties with a default value

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(
            @RequestParam("attachment") List<MultipartFile> files,
            @RequestParam("documentNumber") Integer documentNumber,
            @RequestParam("subject") String subject,
            @RequestParam("dateOfLetter") Date dateOfLetter,
            @RequestParam("type") Integer type,
            @RequestParam("attention") String attention,
            @RequestParam("through") String through,
            @RequestParam("from") String from,
            @RequestParam("pageCount") Integer pageCount,
            @RequestParam("campus") Integer campus,
            @RequestParam("cc") List<String> cc,
            @RequestParam("encoder") Integer encoder,
            @RequestParam("message") String message,
            @RequestParam("departmentId") Integer departmentId,
            @RequestParam(value = "host", required = false) String host, // Optional parameter
            @RequestParam(value = "port", required = false) Integer port, // Optional parameter
            @RequestParam(value = "username", required = false) String username, // Optional parameter
            @RequestParam(value = "password", required = false) String password // Optional parameter
    ) throws MessagingException {

        // Use default values if parameters are not provided
        host = (host != null) ? host : defaultHost;
        port = (port != null) ? port : defaultPort;
        username = (username != null) ? username : defaultUsername;
        password = (password != null) ? password : defaultPassword;

        try {
            // Prepare attachments
            List<String> attachmentPaths = new ArrayList<>();
            for (MultipartFile file : files) {
                String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR, uniqueFileName);
                Files.write(path, file.getBytes());
                attachmentPaths.add(path.toString());
            }

            // Create an Email object
            Email email = new Email();
            email.setDocumentNumber(documentNumber);
            email.setSubject(subject);
            email.setDateOfLetter(dateOfLetter);
            email.setType(type);
            email.setAttention(attention);
            email.setThrough(through);
            email.setFrom(from);
            email.setPageCount(pageCount);
            email.setCampus(campus);
            email.setCc(cc);
            email.setEncoder(encoder);
            email.setMessage(message);
            email.setDepartmentId(departmentId);
            email.setAttachment(attachmentPaths);
            System.out.println(username);
            System.out.println(password);
            // Call the email service with the correct number of arguments
            emailService.sendEmail(email, host, port, username, password);

            return ResponseEntity.ok("Email sent and files uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }
    @GetMapping("/docnum")
    public Integer getDocumentNumber(){
        return emailService.documentNumberCount();
    }

    @GetMapping("/specificemail")
    public ResponseEntity<String> getEmailAddress(@RequestParam("name") String name){
        String emailAddress = emailService.getEmail(name);
        return ResponseEntity.ok(emailAddress);
    }

    @PostMapping("/sendGroup")
    public ResponseEntity<String> sendGroupEmail(
            @RequestParam("attachment") List<MultipartFile> files,
            @RequestParam("documentNumber") Integer documentNumber,
            @RequestParam("subject") String subject,
            @RequestParam("dateOfLetter") Date dateOfLetter,
            @RequestParam("type") Integer type,
            @RequestParam("attention") List<String> attention,
            @RequestParam("through") String through,
            @RequestParam("from") String from,
            @RequestParam("pageCount") Integer pageCount,
            @RequestParam("campus") Integer campus,
            @RequestParam("cc") List<String> cc,
            @RequestParam("encoder") Integer encoder,
            @RequestParam("message") String message,
            @RequestParam("departmentId") Integer departmentId,
            @RequestParam(value = "host", required = false) String host, // Optional parameter
            @RequestParam(value = "port", required = false) Integer port, // Optional parameter
            @RequestParam(value = "username", required = false) String username, // Optional parameter
            @RequestParam(value = "password", required = false) String password // Optional parameter
             ) throws MessagingException {
            host = (host != null) ? host : defaultHost;
            port = (port != null) ? port : defaultPort;
            username = (username != null) ? username : defaultUsername;
            password = (password != null) ? password : defaultPassword;
        try {
            // Create the local folder if it does not exist
            List<String> fileNames = new ArrayList<>();
            for (MultipartFile file : files) {
                String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR, uniqueFileName);

                Files.write(path, file.getBytes());
                fileNames.add(uniqueFileName);
            }

            // List to hold file paths
            List<String> attachmentPaths = new ArrayList<>();
            for (MultipartFile file : files) {
                // Create the path to the file
                Path path = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
                Files.createDirectories(path.getParent());
                // Write the file to the local folder
                Files.write(path, file.getBytes());

                // Add the file path to the list
                attachmentPaths.add(path.toString());
            }

            // Construct the Email object
            DocumentGroup email = new DocumentGroup();
            email.setAttachment(attachmentPaths);;
            email.setDocumentNumber(documentNumber);
            email.setSubject(subject);
            email.setDateOfLetter(dateOfLetter);
            email.setType(type);
            email.setAttention(attention);
            email.setThrough(through);
            email.setFrom(from);
            email.setNumberOfPages(pageCount);
            email.setCampus(campus);
            email.setCc(cc);
            email.setEncoder(encoder);
            email.setMessage(message);
            email.setDepartment(departmentId);

            // Call the service method to send the email
            documentService.sendGroupDocument(email, host, port, username, password);

            return ResponseEntity.ok("Email sent and files uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @PostMapping("/sendMultiple")
    public ResponseEntity<String> sendMultipleEmail(
            @RequestParam("attachment") List<MultipartFile> files,
            @RequestParam("documentNumber") Integer documentNumber,
            @RequestParam("subject") String subject,
            @RequestParam("dateOfLetter") Date dateOfLetter,
            @RequestParam("type") Integer type,
            @RequestParam("attention") List<String> attention,
            @RequestParam("through") String through,
            @RequestParam("from") String from,
            @RequestParam("pageCount") Integer pageCount,
            @RequestParam("campus") Integer campus,
            @RequestParam("cc") List<String> cc,
            @RequestParam("encoder") Integer encoder,
            @RequestParam("message") String message,
            @RequestParam("departmentId") Integer departmentId,
            @RequestParam(value = "host", required = false) String host, // Optional parameter
            @RequestParam(value = "port", required = false) Integer port, // Optional parameter
            @RequestParam(value = "username", required = false) String username, // Optional parameter
            @RequestParam(value = "password", required = false) String password) throws MessagingException {
        host = (host != null) ? host : defaultHost;
        port = (port != null) ? port : defaultPort;
        username = (username != null) ? username : defaultUsername;
        password = (password != null) ? password : defaultPassword;
        try {
            List<String> fileNames = new ArrayList<>();
            for (MultipartFile file : files) {
                String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR, uniqueFileName);

                Files.write(path, file.getBytes());
                fileNames.add(uniqueFileName);
            }

            // List to hold file paths
            List<String> attachmentPaths = new ArrayList<>();
            for (MultipartFile file : files) {
                // Create the path to the file
                Path path = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
                Files.createDirectories(path.getParent());
                // Write the file to the local folder
                Files.write(path, file.getBytes());

                // Add the file path to the list
                attachmentPaths.add(path.toString());
            }

            // Construct the Email object
            DocumentGroup email = new DocumentGroup();
            email.setAttachment(attachmentPaths);;
            email.setDocumentNumber(documentNumber);
            email.setSubject(subject);
            email.setDateOfLetter(dateOfLetter);
            email.setType(type);
            email.setAttention(attention);
            email.setThrough(through);
            email.setFrom(from);
            email.setNumberOfPages(pageCount);
            email.setCampus(campus);
            email.setCc(cc);
            email.setEncoder(encoder);
            email.setMessage(message);
            email.setDepartment(departmentId);
            System.out.println(username);
            System.out.println(password);
            // Call the service method to send the email
            documentService.sendGroupDocument(email, host, port, username, password);

            return ResponseEntity.ok("Email sent and files uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }
}
