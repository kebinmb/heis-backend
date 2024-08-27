package com.chmsu.heis.controller;

import com.chmsu.heis.model.document.Document;
import com.chmsu.heis.model.document.DocumentGroup;
import com.chmsu.heis.model.document.DocumentMultiple;
import com.chmsu.heis.services.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;

    @GetMapping("/documentNumber")
    public ResponseEntity<Integer> getNextDocumentNumber() {
        try {
            Integer nextDocumentNumber = documentService.getNextDocumentNumber();
            return ResponseEntity.ok(nextDocumentNumber);
        } catch (Exception e) {
            logger.error("Error occurred while fetching next document number", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/individual")
    public ResponseEntity<Document> sendToIndividual(@RequestBody Document document) {
        try {
            Integer nextDocumentNumber = documentService.getNextDocumentNumber();
            document.setDocumentNumber(nextDocumentNumber);
            documentService.sendIndividualDocument(document);
            return ResponseEntity.ok(document);
        } catch (Exception e) {
            logger.error("Error occurred while fetching next document number", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }
//    @PostMapping("/sgd")
//    public ResponseEntity<DocumentGroup> sendGroupDocument(@RequestBody DocumentGroup documentGroup) {
//       try{
//           DocumentGroup sentDocumentGroup = documentService.sendGroupDocument(documentGroup);
//           return ResponseEntity.ok(sentDocumentGroup);
//       }catch (Exception e){
//           logger.error("Error occurred while fetching next document number", e);
//           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//       }
//    }
//    @PostMapping("/smd")
//    public ResponseEntity<DocumentMultiple> sendMultipleDocument(@RequestBody DocumentMultiple documentMultiple) {
//        try{
//            DocumentMultiple sentDocumentMultiple = documentService.sendMultipleDocument(documentMultiple);
//            return ResponseEntity.ok(sentDocumentMultiple);
//        }catch (Exception e){
//            logger.error("Error occurred while fetching next document number", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

}
