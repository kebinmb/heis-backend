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
    @Query(value = "INSERT INTO document (document_number, subject, date_of_letter, type, attention, through, `from`, page_count, attachment, campus, department_id, cc, encoder,message) VALUES (:documentNumber, :subject, :dateOfLetter, :type, :attention, :through, :from, :pageCount, :attachment, :campus, :departmentId, :cc, :encoder,:message)", nativeQuery = true)
    void saveEmail(@Param("documentNumber") Integer documentNumber,
                   @Param("subject") String subject,
                   @Param("dateOfLetter") String dateOfLetter,
                   @Param("type") Integer type,
                   @Param("attention") String attention,
                   @Param("through") String through,
                   @Param("from") String from,
                   @Param("pageCount") Integer pageCount,
                   @Param("attachment") String attachment,
                   @Param("campus") Integer campus,
                   @Param("departmentId") Integer departmentId,
                   @Param("cc") String cc,
                   @Param("encoder") Integer encoder,
                   @Param("message") String message);

    @Query(value="SELECT user_id FROM user WHERE email=:email",nativeQuery = true)
    String getUserId(String email);
}
