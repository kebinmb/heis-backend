package com.chmsu.heis.controller;

import com.chmsu.heis.model.document.User;
import com.chmsu.heis.repository.UserRepository;
import com.chmsu.heis.services.UserService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private UserService userService;

    //Controller for getting FACULTY DETAILS
    @GetMapping("/faculty")
    public ResponseEntity<List<User>> getAllFaculty() {
       try{
           List<User> allFaculty = userService.getFacultyDetails();
           return ResponseEntity.ok(allFaculty);
       }
       catch (Exception e){
           logger.error("Error occurred while fetching faculty details", e);
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
       }
    }

    //Controller for getting FACULTY EMAILS ONLY
    @GetMapping("/femail")
    public ResponseEntity<List<String>> getAllFacultyEmails(){
        try{
            List<String> facultyEmails = userService.getFacultyEmails();
            return ResponseEntity.ok(facultyEmails);
        }
        catch(Exception e){
            logger.error("Error occured while fetching faculty emails",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //Controller for getting STAFF DETAILS
    @GetMapping("/staff")
    public ResponseEntity<List<User>> getAllStaffDetails(){
        try{
            List<User> staffDetails = userService.getStaffDetails();
            return ResponseEntity.ok(staffDetails);
        }
        catch(Exception e){
            logger.error("Error occured while fetching staff details",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //Controller for getting STAFF EMAILS
    @GetMapping("/semail")
    public ResponseEntity<List<String>> getAllStaffEmails(){
        try{
            List<String> staffEmails = userService.getStaffEmails();
            return ResponseEntity.ok(staffEmails);
        }catch(Exception e){
            logger.error("Error occured while fetching staff emails");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/regfaculty")
    public ResponseEntity<List<String>> getRegularFacultyEmails(){
        try{
            List<String> regularFacultyEmails = userService.getRegularFacultyEmails();
            return ResponseEntity.ok(regularFacultyEmails);
        }catch (Exception e){
            logger.error("Error occured while fetching regular faculty emails");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/ptfaculty")
    public ResponseEntity<List<String>> getPartTimeFacultyEmails(){
        try{
            List<String> partTimeFacultyEmails = userService.getPartTimeFacultyEmails();
            return ResponseEntity.ok(partTimeFacultyEmails);
        }catch (Exception e){
            logger.error("Error occured while fetching part-time faculty emails");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/regstaff")
    public ResponseEntity<List<String>> getRegularStaffEmails(){
        try{
            List<String> regularStaffEmails = userService.getRegularStaffEmails();
            return ResponseEntity.ok(regularStaffEmails);
        }catch (Exception e){
            logger.error("Error occured while fetching regular staff emails");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/jostaff")
    public ResponseEntity<List<String>> getJobOrderStaffEmails(){
        try{
            List<String> jobOrderStaffEmails = userService.getJobOrderStaffEmails();
            return ResponseEntity.ok(jobOrderStaffEmails);
        }catch (Exception e){
            logger.error("Error occured while fetching regular staff emails");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestParam String username,
                                              @RequestParam String password) {
        User user = userService.findUser(username, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials or insufficient access level");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUsers = userService.getAllUser();
        return ResponseEntity.ok(allUsers);
    }
}
