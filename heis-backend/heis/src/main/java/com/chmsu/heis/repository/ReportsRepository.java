package com.chmsu.heis.repository;

import com.chmsu.heis.model.document.MonthlyReports;
import com.chmsu.heis.model.document.Reports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Repository
public interface ReportsRepository extends JpaRepository<Reports,Long> {

    //Redo this tomorrow. May JOIN sa query
    //Double check basi okay naman ni
    @Query(value="SELECT * FROM document WHERE DATE(document.timestamp) =:date;",nativeQuery = true)
    List<Reports> dailyReport(@Param("date") String date);



}
