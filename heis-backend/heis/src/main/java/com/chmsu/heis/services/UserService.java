package com.chmsu.heis.services;

import com.chmsu.heis.model.document.JwtUtil;
import com.chmsu.heis.model.document.User;
import com.chmsu.heis.model.document.UserToken;
import com.chmsu.heis.repository.LogsRepository;
import com.chmsu.heis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private LogsRepository logsRepository;

    private JwtUtil jwtUtil;




    public UserService(LogsRepository logsRepository, JwtUtil jwtUtil){
        this.logsRepository = logsRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<User> getFacultyDetails() {
        try {
            return userRepository.getAllFacultyDetails();
        } catch (Exception e) {
            throw new RuntimeException("System encountered an exception", e);
        }
    }


    public List<String> getFacultyEmails(){
        try{
            return userRepository.getFacultyEmails();
        }catch (Exception e){
            throw new RuntimeException("System encountered an exception",e);
        }
    }

    public List<User> getStaffDetails(){
        try{
            return userRepository.getAllStaffDetails();
        }catch (Exception e){
            throw new RuntimeException("System encountered an exception",e);
        }
    }

    public List<String> getStaffEmails(){
        try{
            return userRepository.getStaffEmails();
        }catch (Exception e){
            throw new RuntimeException("System encountered an exception",e);
        }
    }

    public List<String> getRegularFacultyEmails(){
        try{
            return userRepository.getRegularFacultyEmails();
        }catch (Exception e){
            throw new RuntimeException("System encountered an exception",e);
        }
    }

    public List<String> getPartTimeFacultyEmails(){
        try{
            return userRepository.getPartTimeFacultyEmails();
        }catch (Exception e){
            throw new RuntimeException("System encountered an exception",e);
        }
    }

    public List<String> getRegularStaffEmails(){
        try{
            return userRepository.getRegularStaffEmails();
        }catch (Exception e){
            throw new RuntimeException("System encountered an exception",e);
        }
    }

    public List<String> getJobOrderStaffEmails(){
        try{
            return userRepository.getJobOrderStaffEmails();
        }catch (Exception e){
            throw new RuntimeException("System encountered an exception",e);
        }
    }


    public UserToken findUser(String username, String password) {
        Long accessLevel = userRepository.findAccessLevelByName(username);
        java.util.Date utilDate = new java.util.Date();
        User user = userRepository.findByUsernameAndPasswordAndAccessLevel(username, password, accessLevel);
        System.out.println(user);
        if (user != null) {
            // Generate JWT token
            System.out.println(accessLevel);
            String token = jwtUtil.generateToken(username);
            Date sqlDate = new Date(utilDate.getTime());
            Long userId = userRepository.findUserIdByName(username);
            logsRepository.insertLogs(
                    userId,
                    "Login Successful for User:" + username,
                    sqlDate
            );
            return new UserToken(user, token);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public List<User> getAllUser(){
        return userRepository.getAllUser();
    }

    //Delete Department
    public void deleteUserById(Long userId) {
        try {
            java.util.Date utilDate = new java.util.Date();
            Date sqlDate = new Date(utilDate.getTime());

            // Log the addition of the new department
            logsRepository.insertLogs(
                    userId,
                    "Deleted a Department",
                    sqlDate
            );
            userRepository.deleteById(userId);

        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Department not found with id: " + userId, e);
        } catch (Exception e) {
            throw new RuntimeException("System encountered an Exception", e);
        }
    }
}
