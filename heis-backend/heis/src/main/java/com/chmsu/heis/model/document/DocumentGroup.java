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
    private String type;
    private String through;

    @ElementCollection
    @CollectionTable(name = "attention", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "attention_value")
    private List<String> to;

    private Integer campus;
    private Long department;
    private String from;
    private Integer numberOfPages;
    private String attachment;

    @ElementCollection
    @CollectionTable(name = "cc", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "cc_value")
    private List<String> cc;
}
