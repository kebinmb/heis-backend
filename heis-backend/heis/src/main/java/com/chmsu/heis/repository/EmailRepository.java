package com.chmsu.heis.repository;

import com.chmsu.heis.model.document.Email;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email,Long>
{
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO document (document_number, subject, date_of_letter, type, attention, through, `from`, page_count, attachment, campus, department_id, cc, encoder) VALUES (:documentNumber, :subject, :dateOfLetter, :type, :attention, :through, :from, :pageCount, :attachment, :campus, :departmentId, :cc, :encoder)", nativeQuery = true)
    void saveEmail(@Param("documentNumber") Integer documentNumber,
                   @Param("subject") String subject,
                   @Param("dateOfLetter") String dateOfLetter,
                   @Param("type") Integer type,
                   @Param("attention") String attention,
                   @Param("through") Integer through,
                   @Param("from") Integer from,
                   @Param("pageCount") Integer pageCount,
                   @Param("attachment") String attachment,
                   @Param("campus") Integer campus,
                   @Param("departmentId") Integer departmentId,
                   @Param("cc") String[] cc,
                   @Param("encoder") Integer encoder);
}
//Method for sending logs
