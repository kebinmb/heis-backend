package com.chmsu.heis.services;

import com.chmsu.heis.model.document.Department;
import com.chmsu.heis.model.document.NewUser;
import com.chmsu.heis.model.document.User;
import com.chmsu.heis.repository.DepartmentRepository;
import com.chmsu.heis.repository.LogsRepository;
import com.chmsu.heis.repository.NewUserRepository;
import com.chmsu.heis.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    private NewUserRepository newUserRepository;
    private LogsRepository logsRepository;
    private UserRepository userRepository;

    public DepartmentService(NewUserRepository newUserRepository, LogsRepository logsRepository, UserRepository userRepository) {
        this.newUserRepository = newUserRepository;
        this.logsRepository = logsRepository;
        this.userRepository = userRepository;
    }

    //Getting all the Department Details
    public List<Department> getAllDepartmentDetails(){
        try{
            List<Department> departmentDetails = departmentRepository.getAllDepartments();
            return  departmentDetails;
        }catch (Exception e){
            throw new RuntimeException("System countered an Exception",e);
        }
    }

    //Add new Department
    public Department addNewDepartment(Department department){
      try{
          departmentRepository.addNewDepartment(department.getDepartmentCode(), department.getDepartmentTitle(), department.getEmailReceiver(), department.getCampus());
          java.util.Date utilDate = new java.util.Date();
          Date sqlDate = new Date(utilDate.getTime());

          // Log the addition of the new department
          logsRepository.insertLogs(
                  department.getDepartmentId(),
                  "Added a New Department",
                  sqlDate
          );
          return department;
      }catch (Exception e){
          throw new RuntimeException("System encountered an Exception",e);
      }

    }

    //Edit Department
   public Department editDepartmentDetails(Department department, Long departmentId){
        try{
            departmentRepository.updateDepartmentDetails(department.getDepartmentCode(),department.getDepartmentTitle(),department.getEmailReceiver(),departmentId);
            java.util.Date utilDate = new java.util.Date();
            Date sqlDate = new Date(utilDate.getTime());

            // Log the addition of the new department
            logsRepository.insertLogs(
                    department.getDepartmentId(),
                    "Edit a Department",
                    sqlDate
            );
            return department;
        }catch(Exception e){
            throw new RuntimeException("System encountered an Exception",e);
        }
   }

   //Delete Department
   public void deleteDepartmentById(Long departmentId) {
       try {
           java.util.Date utilDate = new java.util.Date();
           Date sqlDate = new Date(utilDate.getTime());

           // Log the addition of the new department
           logsRepository.insertLogs(
                   departmentId,
                   "Deleted a Department",
                   sqlDate
           );
           departmentRepository.deleteById(departmentId);

       } catch (EmptyResultDataAccessException e) {
           throw new RuntimeException("Department not found with id: " + departmentId, e);
       } catch (Exception e) {
           throw new RuntimeException("System encountered an Exception", e);
       }
   }

    //Delete User
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


    public NewUser addNewUser(NewUser newUser) {
        try {
            // Ensure the username and other required fields are set
            if (newUser.getUserName() == null || newUser.getUserName().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be null or empty");
            }

            // Call the repository to add the new user
            newUserRepository.addNewUser(
                    newUser.getUserId(),
                    newUser.getUserName(),
                    newUser.getPassword(),
                    newUser.getName(),
                    newUser.getDesignation(),
                    newUser.getDepartmentId(),
                    newUser.getCampus(),
                    newUser.getCompanyName(),
                    newUser.getAccessLevel(),
                    newUser.getEmployeeType(),
                    newUser.getPermanent(),
                    newUser.getUserType(),
                    newUser.getEmailReceiver(),
                    newUser.getEmail()
            );
            java.util.Date utilDate = new java.util.Date();
            Date sqlDate = new Date(utilDate.getTime());

            // Log the addition of the new department
            logsRepository.insertLogs(
                    departmentRepository.getTotalUser().longValue()+1,
                    "Added a new recipient",
                    sqlDate
            );
            return newUser;
        } catch (DataAccessException dae) {
            throw new RuntimeException("Database error occurred", dae);
        } catch (Exception e) {
            throw new RuntimeException("System encountered an error", e);
        }
    }

    public Integer getTotalUser(){
        return this.departmentRepository.getTotalUser();
    }

    //Edit User
    public User editUserDetails(User user, Long userId){
        try{
            userRepository.updateUserDetails(
                    user.getName(),
                    user.getDesignation(),
                    user.getDepartmentId(),
                    user.getCampus(),
                    user.getCompanyName(),
                    user.getAccessLevel(),
                    user.getEmployeeType(),
                    user.getPermanent(),
                    user.getEmailReceiver(),
                    user.getEmail(),
                    user.getUserId());
            java.util.Date utilDate = new java.util.Date();
            Date sqlDate = new Date(utilDate.getTime());

            // Log the addition of the new department
            logsRepository.insertLogs(
                    user.getUserId(),
                    "Edit a Department",
                    sqlDate
            );
            return user;
        }catch(Exception e){
            throw new RuntimeException("System encountered an Exception",e);
        }
    }



}
