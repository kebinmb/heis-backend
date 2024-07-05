package com.chmsu.heis.model.documentTracker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class DocumentTrackerEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    private Long departmentId;
    private String employeeName;
    private String designation;
    private Long campusId;
    private String companyName;
    private Long accessLevel;
    private String employeeType;
    private String status;
    private String userType;
    private String userName;
    private String password;
}
