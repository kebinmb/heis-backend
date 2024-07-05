package com.chmsu.heis.repository;

import com.chmsu.heis.model.document.Document;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.sql.Date;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    //Getting the Document Number
    @Query(value = "SELECT document_number FROM document ORDER BY document_id DESC LIMIT 1;", nativeQuery = true)
    Integer getNextDocumentNumber();



    //POST method for sending email for INDIVIDUAL RECEIVER
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO document (document_number, subject, date_of_letter, type, attention, through, `from`, page_count, attachment, campus, department_id, cc, action_date, status, date_finished, encoder, timestamp) " +
            "VALUES (:documentNumber, :subject, :dateOfLetter, :type, :attention, :through, :from, :pageCount, :attachment, :campus, :departmentId, :cc, :actionDate, :status, :dateFinished, :encoder, :timestamp)",
            nativeQuery = true)
    void insertDocument(
            @Param("documentNumber") int documentNumber,
            @Param("subject") String subject,
            @Param("dateOfLetter") Date dateOfLetter,
            @Param("type") int type,
            @Param("attention") String attention,
            @Param("through") String through,
            @Param("from") String from,
            @Param("pageCount") int pageCount,
            @Param("attachment") String attachment,
            @Param("campus") int campus,
            @Param("departmentId") int departmentId,
            @Param("cc") String cc, // Note: This is now a JSON string
            @Param("actionDate") Date actionDate,
            @Param("status") int status,
            @Param("dateFinished") Date dateFinished,
            @Param("encoder") int encoder,
            @Param("timestamp") Date timestamp
    );


}




