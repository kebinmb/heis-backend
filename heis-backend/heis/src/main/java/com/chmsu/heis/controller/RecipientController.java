package com.chmsu.heis.controller;

import com.chmsu.heis.model.document.Recipient;
import com.chmsu.heis.services.RecipientService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipient")
public class RecipientController {
    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    RecipientService recipientService;

    @GetMapping("/recipients")
    public ResponseEntity<List<Recipient>> getAllRecipient(){
        try{
            List<Recipient> recipients = recipientService.getRecipient();
            return ResponseEntity.ok(recipients);
        }catch (Exception e){
            logger.error("Error occurred while fetching next recipient details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/addrec")
    public ResponseEntity<Recipient> addNewRecipient(@RequestBody Recipient recipient) {
        try {
            Recipient newRecipient = recipientService.addNewRecipient(recipient);
            return new ResponseEntity<>(newRecipient, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
