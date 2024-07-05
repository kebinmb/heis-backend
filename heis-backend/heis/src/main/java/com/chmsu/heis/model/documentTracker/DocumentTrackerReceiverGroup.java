package com.chmsu.heis.model.documentTracker;

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
public class DocumentTrackerReceiverGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiverGroupId;
    private Long documentId;
    private Long employeeId;
    private String status;
    private String action;
    private String remarks;
    private Date dateReceived;
    private Date timeReceived;
}
