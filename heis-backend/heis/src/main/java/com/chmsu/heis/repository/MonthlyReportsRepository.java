package com.chmsu.heis.repository;

import com.chmsu.heis.model.document.MonthlyReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyReportsRepository extends JpaRepository<MonthlyReports,Long> {

    //Redo this tomorrow. May JOIN sa query
    @Query(value = "SELECT document.from, document.department_id, document.attention, document.document_number, document.document_id, user.name, COUNT(document.document_id) AS doc_count " +
            "FROM document " +
            "INNER JOIN user ON user.user_id = document.from " +
            "INNER JOIN department ON department.department_id = user.department_id " +
            "WHERE YEAR(document.timestamp) = :year AND MONTH(document.timestamp) = :month " +
            "GROUP BY document.from, document.attention", nativeQuery = true)
    List<Object[]> monthlyReport(@Param("month") String month,
                                 @Param("year") String year);





}
