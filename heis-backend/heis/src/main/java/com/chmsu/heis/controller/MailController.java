//package com.chmsu.heis.controller;
//
//import com.chmsu.heis.model.config.MailProperties;
//import com.chmsu.heis.model.config.MailPropertiesDto;
//import com.chmsu.heis.services.MailService;
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/mail/config")
//public class MailController {
//
//    @Autowired
//    private MailService mailService;
//
//    @PostMapping("/update")
//    public void updateMailProperties(@RequestBody MailPropertiesDto mailPropertiesDto){
//        mailService.updateMailProperties(mailPropertiesDto.getUsername(),mailPropertiesDto.getPassword());
//    }
//
//    @GetMapping("/currentconfig")
//    public MailProperties getCurrentMailProperties(){
//        return mailService.getMailProperties();
//    }
//
//
//}
