package com.chmsu.heis.model.document;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


import java.util.List;

@Entity
@Data
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer documentNumber;
    private String subject;
    private String dateOfLetter;
    private Integer type;
    private String attention;//to
    private Integer through;
    private Integer from;
    private Integer pageCount;
    private String attachment;
    private Integer campus;
    private Integer departmentId;
    private String[] cc;
    private Integer encoder;
    private String message;


//    private String to;
//    private String cc;
//    private String subject;
//    private String message;
//    private String attachment;



    // Getters and Setters
}
