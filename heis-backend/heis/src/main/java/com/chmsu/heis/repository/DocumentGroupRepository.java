package com.chmsu.heis.repository;

import com.chmsu.heis.model.document.DocumentGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface DocumentGroupRepository extends JpaRepository<DocumentGroup,Long> {


    //POST method for sending email for GROUP RECEIVER
    //To: should be in an array for it will send by group.
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO document (document_number, subject, date_of_letter, type, through, `attention`, campus, department_id, `from`, page_count, attachment, cc) " +
            "VALUES (:documentNumber, :subject, :dateOfLetter, :type, :through, :to, :campus, :departmentId, :from, :numberOfPages, :attachment, :cc)", nativeQuery = true)
    void sendDocumentToGroup(@Param("documentNumber") Integer documentNumber,
                             @Param("subject") String subject,
                             @Param("dateOfLetter") Date dateOfLetter,
                             @Param("type") String type,
                             @Param("through") String through,
                             @Param("to") String to,//attention
                             @Param("campus") Integer campus,
                             @Param("departmentId") Long departmentId,
                             @Param("from") String from,
                             @Param("numberOfPages") Integer numberOfPages,
                             @Param("attachment") String attachment,
                             @Param("cc") String cc);

}
