package com.chmsu.heis.model.document;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    private String name;
    private String designation;
    private Long departmentId;
    private Long campus;
    private String companyName;
    private Long accessLevel;
    private String employeeType;
    private Long permanent;
    private Long userType;
    private Long emailReceiver;
    private String email;
}
