package com.chmsu.heis.services;

import com.chmsu.heis.model.document.Recipient;
import com.chmsu.heis.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipientService {

    @Autowired
    RecipientRepository recipientRepository;

    public List<Recipient> getRecipient(){
      try{
          List<Recipient> newRecipient = recipientRepository.getAllTheRecipients();
          return newRecipient;
      }catch (Exception e){
          throw new RuntimeException("System countered an Exception",e);
      }
    }

    public Recipient addNewRecipient(Recipient recipient) {
        try {
            recipientRepository.addNewRecipient(
                    recipient.getPassword(),
                    recipient.getUsername(),
                    recipient.getName(),
                    recipient.getDesignation(),
                    recipient.getDepartmentId(),
                    recipient.getCampus(),
                    recipient.getCompanyName(),
                    recipient.getAccessLevel(),
                    recipient.getEmployeeType(),
                    recipient.getPermanent(),
                    recipient.getUserType(),
                    recipient.getEmailReceiver(),
                    recipient.getEmail()
            );

            // Assuming the email is unique, you can retrieve the newly added recipient
            return recipient;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("System encountered an issue", e);
        }
    }
}

