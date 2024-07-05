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
public class DocumentTrackerCampus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long campusId;
    private String campusName;
}
