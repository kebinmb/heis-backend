package com.chmsu.heis.services;


import com.chmsu.heis.model.document.ExternalReports;
import com.chmsu.heis.model.document.MonthlyReports;
import com.chmsu.heis.model.document.Reports;
import com.chmsu.heis.model.document.UserDetailsForMonthlyReports;
import com.chmsu.heis.repository.ExternalReportsRepository;
import com.chmsu.heis.repository.MonthlyReportsRepository;
import com.chmsu.heis.repository.ReportsRepository;
import com.chmsu.heis.repository.UserDetailsForMonthlyReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportsRepository reportsRepository;
    @Autowired
    private MonthlyReportsRepository monthlyReportsRepository;
    @Autowired
    private ExternalReportsRepository externalReportsRepository;
    @Autowired
    private UserDetailsForMonthlyReportsRepository userDetailsForMonthlyReportsRepository;



    public List<Reports> getReports(LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return reportsRepository.dailyReport(formattedDate);
    }

    public List<MonthlyReports> getMonthlyReports(String month, String year) {
        try {
            List<Object[]> results = monthlyReportsRepository.monthlyReport(month, year);
            List<MonthlyReports> reports = new ArrayList<>();

            for (Object[] result : results) {
                MonthlyReports report = new MonthlyReports();
                report.setFrom((String) result[0]);
                report.setDepartmentId((Integer) result[1]);
                report.setAttention((String) result[2]); // Ensure attention is mapped correctly
                report.setDocumentNumber((Integer) result[3]);
                report.setDocumentId((Integer) result[4]);
                report.setName((String) result[5]);
                report.setDocCount(((Number) result[6]).intValue());

                reports.add(report);
            }
            return reports;
        } catch (Exception e) {
            throw new RuntimeException("System encountered an exception", e);
        }
    }

    public List<ExternalReports> getExternalMonthlyReports(String externalMonth, String externalYear){
        try{
            System.out.println(externalMonth);
            return externalReportsRepository.monthlyReportExternal(externalMonth,externalYear);
        }catch (Exception e){
            throw new RuntimeException("System encountered an exception",e);
        }
    }

    public List<UserDetailsForMonthlyReports> getUsers(){
        try{
            return userDetailsForMonthlyReportsRepository.getReceiverName();
        }catch (Exception e)
        {
            throw new RuntimeException("System encountered an exception",e);
        }
    }
}
