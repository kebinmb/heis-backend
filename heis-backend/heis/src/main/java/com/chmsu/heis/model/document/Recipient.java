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
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String username;
    private String password;
    private String name;
    private String designation;
    private Integer departmentId;
    private Integer campus;
    private String companyName;
    private Integer accessLevel;
    private String employeeType;
    private Integer permanent;
    private Integer userType;
    private Integer emailReceiver;
    private String email;
    private Integer campusId;
    private String campusName;
//    private Integer departmentId(1);
    private String departmentCode;
    private String departmentTitle;
//    private Integer emailReceiver(1);
//    private Integer campus(1);

}
