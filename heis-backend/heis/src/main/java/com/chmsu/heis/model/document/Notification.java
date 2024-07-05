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
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private Long sender;
    private Long receiver;
    private Long documentId;
    //private Long viewed;
    private Date timestamp;

    // Constructors, getters, and setters are automatically generated by Lombok
}
