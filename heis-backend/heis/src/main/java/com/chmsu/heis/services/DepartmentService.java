package com.chmsu.heis.services;

import com.chmsu.heis.model.document.Department;
import com.chmsu.heis.model.document.NewUser;
import com.chmsu.heis.repository.DepartmentRepository;
import com.chmsu.heis.repository.NewUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    private NewUserRepository newUserRepository;

    public DepartmentService(NewUserRepository newUserRepository) {
        this.newUserRepository = newUserRepository;
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
          return department;
      }catch (Exception e){
          throw new RuntimeException("System encountered an Exception",e);
      }
    }

    //Edit Department
   public Department editDepartmentDetails(Department department, Long departmentId){
        try{
            departmentRepository.updateDepartmentDetails(department.getDepartmentCode(),department.getDepartmentTitle(),department.getEmailReceiver(),departmentId);
            return department;
        }catch(Exception e){
            throw new RuntimeException("System encountered an Exception",e);
        }
   }

   //Delete Department
   public void deleteDepartmentById(Long departmentId) {
       try {
           departmentRepository.deleteById(departmentId);
       } catch (EmptyResultDataAccessException e) {
           throw new RuntimeException("Department not found with id: " + departmentId, e);
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

}
