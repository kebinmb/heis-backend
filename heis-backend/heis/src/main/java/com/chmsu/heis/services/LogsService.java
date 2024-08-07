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
        return logsRepository.getLogs(date, campus);
    }
}
