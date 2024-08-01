package com.chmsu.heis.model.document;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class NewUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "designation")
    private String designation;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "campus")
    private Integer campus;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "access_level")
    private Integer accessLevel;

    @Column(name = "employee_type")
    private String employeeType;

    @Column(name = "permanent")
    private Integer permanent;

    @Column(name = "user_type")
    private Integer userType;

    @Column(name = "email_receiver")
    private Integer emailReceiver;

    @Column(name = "email")
    private String email;
}
