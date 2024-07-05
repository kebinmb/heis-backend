package com.chmsu.heis.services;

import com.chmsu.heis.model.document.Document;
import com.chmsu.heis.model.document.DocumentGroup;
import com.chmsu.heis.model.document.DocumentMultiple;
import com.chmsu.heis.repository.DocumentGroupRepository;
import com.chmsu.heis.repository.DocumentMultipleRepository;
import com.chmsu.heis.repository.DocumentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentGroupRepository documentGroupRepository;
    @Autowired
    private DocumentMultipleRepository documentMultipleRepository;

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

    //Sending email to a group
    public DocumentGroup sendGroupDocument(DocumentGroup documentGroup){
        ObjectMapper objectMapper = new ObjectMapper();
        String ccJson;
        String to;
        try {
            ccJson = objectMapper.writeValueAsString(documentGroup.getCc());
            to = objectMapper.writeValueAsString(documentGroup.getTo());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Handle the error appropriately
            throw new RuntimeException("Failed to convert cc list to JSON", e);
        }
        documentGroupRepository.sendDocumentToGroup(
                documentGroup.getDocumentNumber(),
                documentGroup.getSubject(),
                documentGroup.getDateOfLetter(),
                documentGroup.getType(),
                documentGroup.getThrough(),
                to,
                documentGroup.getCampus(),
                documentGroup.getDepartment(),
                documentGroup.getFrom(),
                documentGroup.getNumberOfPages(),
                documentGroup.getAttachment(),
                ccJson);
        System.out.println(documentGroup.getDocumentNumber() + " From Service");
        return documentGroup;
    }

    //POST method for sending email to multiple receivers
    public DocumentMultiple sendMultipleDocument(DocumentMultiple documentMultiple){
        ObjectMapper objectMapper = new ObjectMapper();
        String ccJson;
        String to;
        try {
            ccJson = objectMapper.writeValueAsString(documentMultiple.getCc());
            to = objectMapper.writeValueAsString(documentMultiple.getTo());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Handle the error appropriately
            throw new RuntimeException("Failed to convert cc list to JSON", e);
        }
        documentMultipleRepository.sendDocumentToMultiple(
                documentMultiple.getDocumentNumber(),
                documentMultiple.getSubject(),
                documentMultiple.getDateOfLetter(),
                documentMultiple.getType(),
                documentMultiple.getThrough(),
                to,
                documentMultiple.getCampus(),
                documentMultiple.getDepartment(),
                documentMultiple.getFrom(),
                documentMultiple.getNumberOfPages(),
                documentMultiple.getAttachment(),
                ccJson);
        System.out.println(documentMultiple.getDocumentNumber() + " From Service");
        return documentMultiple;
    }


}
