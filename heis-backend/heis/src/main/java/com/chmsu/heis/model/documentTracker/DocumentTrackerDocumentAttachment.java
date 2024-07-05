package com.chmsu.heis.model.documentTracker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;


@Data
@NoArgsConstructor
@Entity
public class DocumentTrackerDocumentAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentAttachmentId;
    private Long documentId;
    private String fileName;
    private File filePhoto;
    private String fileType;
    private String fileSize;
}
