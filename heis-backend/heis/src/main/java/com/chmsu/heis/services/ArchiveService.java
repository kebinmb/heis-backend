package com.chmsu.heis.services;

import com.chmsu.heis.model.document.Archive;
import com.chmsu.heis.repository.ArchiveRepository;
import com.chmsu.heis.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchiveService {

    @Autowired
    private ArchiveRepository archiveRepository;
    private LogsRepository logsRepository;
    //Get all Documents
    public List<Archive> getAllDocuments() throws Exception {
       try{
           List<Archive> archives = archiveRepository.getAllDocuments();
//           logsRepository.insertLogs(sessionId.user,message,timestamp);
           return archives;
       }catch (Exception e){
           throw new Exception("Error in retrieving Docuemnts",e);
       }
    }
}
