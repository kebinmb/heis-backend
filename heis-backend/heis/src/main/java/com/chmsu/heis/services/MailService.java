//package com.chmsu.heis.services;
//
//import com.chmsu.heis.model.config.MailProperties;
//import jakarta.annotation.PostConstruct;
//import lombok.Getter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.stereotype.Service;
//
//import java.util.Properties;
//
//@Service
//public class MailService {
//
//    @Getter
//    @Autowired
//    private MailProperties mailProperties;
//
//    @Autowired
//    private JavaMailSenderImpl mailSender;
//
//    @PostConstruct
//    public void init(){
//        updateMailSender();
//    }
//
//    public void updateMailSender(){
//        mailSender.setHost(mailProperties.getHost());
//        mailSender.setPort(mailProperties.getPort());
//        mailSender.setUsername(mailProperties.getUsername());
//        mailSender.setPassword(mailProperties.getPassword());
//
//        Properties properties = mailSender.getJavaMailProperties();
//        properties.put("mail.smtp.auth",mailProperties.isAuth());
//        properties.put("mail.smtp.starttls.enable",mailProperties.isStarttlsEnable());
//    }
//
//    public void updateMailProperties(String username, String password){
//        mailProperties.setUsername(username);
//        mailProperties.setPassword(password);
//        updateMailSender();
//    }
//
//}
