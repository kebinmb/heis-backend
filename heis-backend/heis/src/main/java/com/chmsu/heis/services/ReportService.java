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

    public List<MonthlyReports> getMonthlyReports(String month, String year){
        try{
            System.out.println(month);
            return monthlyReportsRepository.monthlyReport(month,year);
        }catch (Exception e){
            throw new RuntimeException("System encountered an exception",e);
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
