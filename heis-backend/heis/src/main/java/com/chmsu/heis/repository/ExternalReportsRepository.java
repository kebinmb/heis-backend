package com.chmsu.heis.repository;

import com.chmsu.heis.model.document.ExternalReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExternalReportsRepository extends JpaRepository<ExternalReports,Long> {
    @Query(value = "SELECT document.document_id,document.attention,document.from,user.name,document.department_id,user.employee_type,document.document_number,COUNT(document.document_id) AS document_count\n" +
            "FROM document\n" +
            "INNER JOIN user ON user.user_id = document.from\n" +
            "INNER JOIN department ON department.department_id = user.department_id\n" +
            "WHERE YEAR(document.timestamp) = :year\n" +
            "  AND MONTH(document.timestamp) = :month\n" +
            "  AND user.user_type = 2\n" +
            "GROUP BY document.document_id;\n",nativeQuery = true)
    List<ExternalReports> monthlyReportExternal(@Param("month") String month,
                                                @Param("year") String year);

}
