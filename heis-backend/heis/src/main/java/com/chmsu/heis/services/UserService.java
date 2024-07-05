package com.chmsu.heis.services;

import com.chmsu.heis.model.document.User;
import com.chmsu.heis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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


    public User findUser(String username, String password) {
        List<Integer> accessLevels = Arrays.asList(1, 4);
        return userRepository.findByUsernameAndPasswordAndAccessLevel(username, password, accessLevels);
    }

}
