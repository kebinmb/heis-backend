package com.chmsu.heis.controller;


import com.chmsu.heis.model.document.Department;
import com.chmsu.heis.model.document.NewUser;
import com.chmsu.heis.services.DepartmentService;
import com.chmsu.heis.services.LogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DepartmentService departmentService;
    private LogsService logsService;


    @GetMapping("/departdetails")
    public ResponseEntity<List<Department>> getDepartmentDetails(){
        try{
            List<Department> departmentDetails = departmentService.getAllDepartmentDetails();
            return ResponseEntity.ok(departmentDetails);

        }catch(Exception e){
            logger.error("Error occurred while fetching department details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/newdep")
    public ResponseEntity<Department> addNewDepartment(
            @RequestParam("departmentCode") String departmentCode,
            @RequestParam("departmentTitle") String departmentTitle,
            @RequestParam("emailReceiver") Long emailReceiver,
            @RequestParam("campus") Long campus,
            @RequestParam("departmentId") Long departmentId) {

        try {
            Department department = new Department();
            department.setDepartmentCode(departmentCode);
            department.setDepartmentTitle(departmentTitle);
            department.setEmailReceiver(emailReceiver);
            department.setCampus(campus);
            department.setDepartmentId(departmentId);

            // Save department using service (pseudo code)
            departmentService.addNewDepartment(department);

            return ResponseEntity.ok(department);
        } catch (Exception e) {
            logger.error("Error occurred while adding new department", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable("id") Long departmentId, @RequestBody Department department) {
        try {
            Department updatedDepartment = departmentService.editDepartmentDetails(department, departmentId);
            return ResponseEntity.ok(updatedDepartment);
        } catch (RuntimeException e) {
            logger.error("Error occurred while fetching next document number", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable("id") Long departmentId) {
        try {
            departmentService.deleteDepartmentById(departmentId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/new/user")
    public ResponseEntity<NewUser> addNewUser(@RequestBody NewUser newUser) {
        try {
            // Ensure that all required fields are set in the NewUser object
            if (newUser.getUserName() == null || newUser.getUserName().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            // Call the service layer to add the new user
            NewUser addedUser = departmentService.addNewUser(newUser);

            // Return the added user in the response
            return ResponseEntity.ok(addedUser);
        } catch (DataAccessException dae) {
            // Handle database access exceptions
            logger.error("Database error occurred while adding new user", dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("System error occurred while adding new user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
