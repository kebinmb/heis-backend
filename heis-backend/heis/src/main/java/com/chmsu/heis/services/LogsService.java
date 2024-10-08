package com.chmsu.heis.services;

import com.chmsu.heis.model.document.Logs;
import com.chmsu.heis.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LogsService {
    @Autowired
    private LogsRepository logsRepository;

    //Authentication Logs
    public List<Logs> getLogs(String date, Integer campus) {
        System.out.println("Authentication Logs:"+logsRepository.getLogs(date,campus));
        return logsRepository.getLogs(date, campus);
    }

    //Document Processing Logs
    public List<Logs> getDocumentLogs(String date, Integer campus){
        System.out.println("Document Logs:"+logsRepository.getDocumentLogs(date,campus));
        return logsRepository.getDocumentLogs(date,campus);
    }
    public List<Logs> getDocumentLogsTalisayAccess(String date){
        return logsRepository.getDocumentLogsTalisayAccess(date);
    }

    //User Maintenance Logs
    public List<Logs> getUserMaintenanceLogs(String date, Integer campus){
        System.out.println("User Maintenance Logs:"+logsRepository.getUserMaintenanceLogs(date,campus));
        return logsRepository.getUserMaintenanceLogs(date,campus);
    }

    //User credentials maintenance Logs
    public List<Logs> getUserCredentialsMaintenaceLogs(String date, Integer campus){
        return logsRepository.getUserCredentialsMaintenanceLogs(date,campus);
    }
}
