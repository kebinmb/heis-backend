package com.chmsu.heis.controller;


import com.chmsu.heis.model.document.ExternalReports;
import com.chmsu.heis.model.document.MonthlyReports;
import com.chmsu.heis.model.document.Reports;
import com.chmsu.heis.model.document.UserDetailsForMonthlyReports;
import com.chmsu.heis.repository.MonthlyReportsRepository;
import com.chmsu.heis.services.ReportService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportsController {
    @Autowired
    private ReportService reportService;


    @GetMapping("/daily/{date}")
    public ResponseEntity<List<Reports>> dailyReports(@PathVariable("date")  String date) {
        List<Reports> dailyReports = reportService.getReports(LocalDate.parse(date));
        return ResponseEntity.ok(dailyReports);
    }


    @GetMapping("monthly/{month}/{year}")
    public ResponseEntity<List<MonthlyReports>> monthlyReports(
            @PathVariable("month") String month,
            @PathVariable("year") String year) {

        // Implement logic to fetch monthly reports based on month and year
        List<MonthlyReports> monthlyReports = reportService.getMonthlyReports(month, year);
        return ResponseEntity.ok(monthlyReports);
    }

    @GetMapping("external/{month}/{year}")
    public ResponseEntity<List<ExternalReports>> externalReports(@PathVariable("month") String month,@PathVariable("year")String year){
        System.out.println(month);
        List<ExternalReports> externalMonthlyReports = reportService.getExternalMonthlyReports(month,year);
        return ResponseEntity.ok(externalMonthlyReports);
    }

    @GetMapping("/receivers")
    public  ResponseEntity<List<UserDetailsForMonthlyReports>> getReceivers(){
        List<UserDetailsForMonthlyReports> receivers = reportService.getUsers();
        return ResponseEntity.ok(receivers);
    }



}
