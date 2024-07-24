package com.chmsu.heis.model.document;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
public class Archive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer documentId;
    Integer documentNumber;
    String subject;
    Date dateOfLetter;
    Integer type;
    String attention;
    String through;
    String from;
    Integer pageCount;
    String attachment;
    Integer campus;
    Integer departmentId;
    String cc;
    Integer encoder;
    String timestamp;
    String message;
}
