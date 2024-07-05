package com.chmsu.heis.repository;

import com.chmsu.heis.model.document.Recipient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {

    @Query(value = "SELECT u.user_id,u.campus,u.company_name, u.username,u.name, u.email, u.access_level, u.email_receiver,u.designation,u.employee_type,u.password,u.permanent,u.user_type, \n" +
            "       c.campus_id AS campus_id, c.campus_name, \n" +
            "       d.department_id AS department_id, d.department_title AS department_title, d.department_code AS department_code\n" +
            "FROM user u\n" +
            "INNER JOIN campus c ON c.campus_id = u.campus\n" +
            "INNER JOIN department d ON d.department_id = u.department_id\n" +
            "WHERE u.access_level = '3' AND u.email_receiver = '1'\n" +
            "ORDER BY u.username;", nativeQuery = true)
    List<Recipient> getAllTheRecipients();


    //Adding new Recipient
    @Modifying
    @Transactional
    @Query(value="INSERT INTO user (username,password,name, designation, department_id, campus, company_name, access_level, employee_type, permanent, user_type, email_receiver, email) VALUES (:username,:password,:name, :designation, :departmentId, :campus, :companyName, :accessLevel, :employeeType, :permanent, :userType, :emailReceiver, :email)", nativeQuery = true)
    void addNewRecipient(
            @Param("username")String username,
            @Param("password")String password,
            @Param("name") String name,
            @Param("designation") String designation,
            @Param("departmentId") Integer departmentId,
            @Param("campus") Integer campus,
            @Param("companyName") String companyName,
            @Param("accessLevel") Integer accessLevel,
            @Param("employeeType") String employeeType,
            @Param("permanent") Integer permanent,
            @Param("userType") Integer userType,
            @Param("emailReceiver") Integer emailReceiver,
            @Param("email") String email
    );


}
