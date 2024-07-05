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
    @Query(value = "SELECT document.from,document.department_id,document.attention,document.document_number,document.document_id,user.name, COUNT(document.document_id) AS doc_count\n" +
            "FROM document\n" +
            "INNER JOIN user ON user.user_id = document.from\n" +
            "INNER JOIN department ON department.department_id = user.department_id\n" +
            "WHERE YEAR(document.timestamp) = :year AND MONTH(document.timestamp) = :month\n" +
            "GROUP BY document.from\n", nativeQuery = true)
    List<MonthlyReports> monthlyReport(@Param("month") String month,
                                       @Param("year")String year);


   @Query(value = "SELECT document.document_id,document.from,user.name,document.department_id,COUNT(document.document_id) AS doc_count\n" +
           "FROM document\n" +
           "INNER JOIN user ON user.user_id = document.from\n" +
           "INNER JOIN department ON department.department_id = user.department_id\n" +
           "WHERE YEAR(document.timestamp) = YEAR(CURRENT_DATE())\n" +
           "  AND MONTH(document.timestamp) = :month\n" +
           "  AND user.user_type = 2\n" +
           "GROUP BY document.document_id;\n",nativeQuery = true)
    List<MonthlyReports> monthlyReportExternal(@Param("month") String month);


}
