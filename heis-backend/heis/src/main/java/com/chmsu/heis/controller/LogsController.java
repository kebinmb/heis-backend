package com.chmsu.heis.controller;

import com.chmsu.heis.model.document.Logs;
import com.chmsu.heis.services.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogsController {

    @Autowired
    private LogsService logsService;

    @GetMapping("/searchlogs")
    public List<Logs> getLogs(@RequestParam String date,
                              @RequestParam Integer campus) {
        return logsService.getLogs(date, campus);
    }

    @GetMapping("/documentLogs")
    public List<Logs> getDocumentLogs(@RequestParam String date, @RequestParam Integer campus){
        return logsService.getDocumentLogs(date,campus);
    }

    @GetMapping("/usermainteLogs")
    public  List<Logs> getUserMaintenanceLogs(@RequestParam String date, @RequestParam Integer campus){
        return logsService.getUserMaintenanceLogs(date,campus);
    }
}
