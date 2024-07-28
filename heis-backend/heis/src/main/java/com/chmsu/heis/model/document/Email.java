package com.chmsu.heis.model.document;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.sql.Date;
import java.util.List;

@Entity
@Data
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer documentNumber;
    private String subject;
    private Date dateOfLetter;
    private Integer type;
    private String attention;
    private String through;
    private String from;
    private Integer pageCount;
    @ElementCollection
    @CollectionTable(name = "attachment", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "attachment_value")
    private List<String> attachment;
    private Integer campus;
    private Integer departmentId;
    @ElementCollection
    @CollectionTable(name = "cc", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "cc_value")
    private List<String> cc;
    private Integer encoder;
    private String message;




//    private String to;
//    private String cc;
//    private String subject;
//    private String message;
//    private String attachment;



    // Getters and Setters
}


