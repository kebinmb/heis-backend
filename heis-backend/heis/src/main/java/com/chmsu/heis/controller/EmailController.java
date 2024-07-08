package com.chmsu.heis.controller;

import com.chmsu.heis.model.document.Email;
import com.chmsu.heis.services.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public void sendEmail(@RequestBody Email email) throws MessagingException, JsonProcessingException {
        emailService.sendEmail(email);
    }

    @GetMapping("/docnum")
    public Integer getDocumentNumber(){
        return emailService.documentNumberCount();
    }
}
