package com.chmsu.heis.repository;

import com.chmsu.heis.model.document.NewUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewUserRepository extends JpaRepository<NewUser, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user (user_id, username, password, name,   designation, department_id, campus, company_name, access_level, employee_type, permanent, user_type, email_receiver, email) " +
            "VALUES (:userId, :username,:password, :name, :designation, :departmentId, :campus, :companyName, :accessLevel, :employeeType, :permanent, :userType, :emailReceiver, :email)",
            nativeQuery = true)
    void addNewUser(
            @Param("userId") Integer userId,
            @Param("username") String username,
            @Param("password") String password,
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
