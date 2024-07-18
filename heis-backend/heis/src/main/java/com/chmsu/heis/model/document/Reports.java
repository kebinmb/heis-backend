package com.chmsu.heis.model.document;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@Entity
public class Reports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer documentNumber;
    private String timestamp;
    private String subject;
    private Integer encoder;
    private Integer type;
    private String from;
    private String attention;
    private String through;

//    private String status;
}


