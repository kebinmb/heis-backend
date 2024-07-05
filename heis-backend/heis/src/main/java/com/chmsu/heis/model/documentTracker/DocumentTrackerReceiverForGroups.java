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
public class DocumentTrackerReceiverForGroups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiverId;
    private Long documentId;
    private String receiverType;
    private String receiverTypeStatus;
    private Long sequenceNumber;
    private String Status;
    private String actions;
    private String remarks;
    private Long employeeId;
    private Long receiverGroupTypeId;
    private Long departmentId;
    private Long campusId;
    private Date timeReceived;
    private Date dateReceived;
}
