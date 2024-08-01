package com.chmsu.heis.repository;

import com.chmsu.heis.model.document.Department;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {



    @Query(value="SELECT * FROM department ORDER BY department_title;",nativeQuery = true)
    List<Department> getAllDepartments();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO department (department_code, department_title, email_receiver, campus) VALUES (:departmentCode, :departmentTitle, :emailReceiver, :campus)", nativeQuery = true)
    void addNewDepartment(
            @Param("departmentCode") String departmentCode,
            @Param("departmentTitle") String departmentTitle,
            @Param("emailReceiver") Long emailReceiver,
            @Param("campus") Long campus
    );

    @Modifying
    @Transactional
    @Query(value="UPDATE department SET department_code =:departmentCode, department_title =:departmentTitle, email_receiver = :emailReceiver WHERE department_id =:departmentId;",nativeQuery = true)
    void updateDepartmentDetails(
            @Param("departmentCode") String departmentCode,
            @Param("departmentTitle") String departmentTitle,
            @Param("emailReceiver") Long emailReceiver,
            @Param("departmentId") Long departmentId
    );


}
