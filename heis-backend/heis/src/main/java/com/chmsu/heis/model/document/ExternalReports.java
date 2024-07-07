package com.chmsu.heis.model.document;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class ExternalReports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer documentId;
    private Integer from;
    private String name;
    private Integer departmentId;
    private Integer documentCount;
    private Integer attention;
    private String employeeType;
    private Integer documentNumber;

}
