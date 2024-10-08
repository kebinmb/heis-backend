package com.chmsu.heis.controller;

import com.chmsu.heis.model.document.AuthenticationRequest;
import com.chmsu.heis.model.document.User;
import com.chmsu.heis.model.document.UserToken;
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


    @PostMapping("/authenticate")
    public ResponseEntity<UserToken> authenticate(@RequestBody AuthenticationRequest request) {
        UserToken userToken = userService.findUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(userToken);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUsers = userService.getAllUser();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping("/updatecreds")
    public ResponseEntity<String> updateUserCred(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String password) {
        if (name == null || password == null) {
            return ResponseEntity.badRequest().body("Required parameters 'name' and 'password' are missing.");
        }

        try {
            // Assuming updateUserCredentials throws an exception if something goes wrong
            userService.updateUserCredentials(name, password);
            return ResponseEntity.ok("Credentials updated successfully");
        } catch (Exception e) {
            // Log the exception and return an appropriate HTTP response
            e.printStackTrace(); // Or use a logging framework
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating credentials");
        }
    }

    @GetMapping("/pw")
    public ResponseEntity<String> getUserPassword(@RequestParam String name){
        String password = userService.getUserPassword(name);
        return ResponseEntity.ok(password);
    }
}
