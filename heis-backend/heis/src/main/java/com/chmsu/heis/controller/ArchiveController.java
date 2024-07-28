package com.chmsu.heis.controller;

import com.chmsu.heis.model.document.Archive;
import com.chmsu.heis.model.document.Department;
import com.chmsu.heis.services.ArchiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/archives")
public class ArchiveController {
    private static final Logger logger = LoggerFactory.getLogger(ArchiveController.class);

    private final String imageFolderPath = "/Users/kevin/Downloads";
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

    @GetMapping("{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Path filePath = Paths.get(imageFolderPath).resolve(filename);
        Resource resource = new FileSystemResource(filePath);

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        try {
            // Determine the file type
            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM; // Default type

            String mimeType = Files.probeContentType(filePath);
            if (mimeType != null) {
                mediaType = MediaType.parseMediaType(mimeType);
            }

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
