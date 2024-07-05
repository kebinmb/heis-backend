package com.chmsu.heis.services;

import com.chmsu.heis.model.document.Logs;
import com.chmsu.heis.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class LogsService {
    @Autowired
    private LogsRepository logsRepository;

    //Authentication Logs
    public Logs getLogs(String date,Integer campus, String keyword){
        return logsRepository.getLogs(date,campus,"%"+ keyword +"%");
    }
}
