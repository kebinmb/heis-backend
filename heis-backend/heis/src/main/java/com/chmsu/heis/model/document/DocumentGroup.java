package com.chmsu.heis.model.document;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class DocumentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer documentNumber;
    private String subject;
    private Date dateOfLetter;
    private Integer type;
    private String through;

    @ElementCollection
    @CollectionTable(name = "attention", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "attention_value")
    private List<String> attention;

    private Integer campus;
    private Integer department;
    private String from;
    private Integer numberOfPages;
    @ElementCollection
    @CollectionTable(name = "attachment", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "attachment_value")
    private List<String> attachment;

    @ElementCollection
    @CollectionTable(name = "cc", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "cc_value")
    private List<String> cc;
    private Integer encoder;
    private String message;
}
