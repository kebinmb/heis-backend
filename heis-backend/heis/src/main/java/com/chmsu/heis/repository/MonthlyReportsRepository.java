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
    @Query(value = "SELECT \n" +
            "    d.from,\n" +
            "    d.department_id,\n" +
            "    d.attention,\n" +
            "    d.document_number,\n" +
            "    d.document_id,\n" +
            "    u.name,\n" +
            "    COUNT(*) OVER (PARTITION BY d.attention) AS doc_count\n" +
            "FROM document d\n" +
            "INNER JOIN user u ON u.user_id = d.from \n" +
            "INNER JOIN department dept ON dept.department_id = u.department_id \n" +
            "WHERE YEAR(d.timestamp) = :year \n" +
            "  AND MONTH(d.timestamp) = :month;\n", nativeQuery = true)
    List<Object[]> monthlyReport(@Param("month") String month,
                                 @Param("year") String year);





}
