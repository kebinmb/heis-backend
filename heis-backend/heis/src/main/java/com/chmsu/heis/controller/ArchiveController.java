package com.chmsu.heis.controller;

import com.chmsu.heis.model.document.Archive;
import com.chmsu.heis.model.document.Department;
import com.chmsu.heis.services.ArchiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/archives")
public class ArchiveController {
    private static final Logger logger = LoggerFactory.getLogger(ArchiveController.class);
    @Autowired
    private ArchiveService archiveService;

    @GetMapping("/documents")
    public ResponseEntity<List<Archive>> getAllDocuments() throws Exception {
        try{
            List<Archive> archives = archiveService.getAllDocuments();
            return ResponseEntity.ok(archives);
        }catch(Exception e){
            logger.error("Error occurred while fetching Archive details from controller", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
