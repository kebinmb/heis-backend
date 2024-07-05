package com.chmsu.heis.services;

import com.chmsu.heis.model.document.Department;
import com.chmsu.heis.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

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

}
