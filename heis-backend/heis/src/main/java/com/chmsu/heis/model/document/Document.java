package com.chmsu.heis.model.document;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Document {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int documentId;

    private int documentNumber;
    private String subject;


    private Date dateOfLetter;

    private int type;
    private String attention;
    private String through;
    private String from;
    private int pageCount;
    private String attachment;
    private int campus;
    private int departmentId;

    @ElementCollection
    @CollectionTable(name = "cc", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "cc_value")
    private List<String> cc;


    private Date actionDate;
    private int Status;


    private Date dateFinished;
    private int encoder;


    private Date timestamp;

    //Additional entity for group sending
    private String to;
    //private String message;


}