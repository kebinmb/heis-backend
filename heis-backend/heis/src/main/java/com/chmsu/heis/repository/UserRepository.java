package com.chmsu.heis.repository;

import com.chmsu.heis.model.document.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    //Getting all the details of FACULTY
    @Query(value="SELECT * FROM user WHERE employee_type = 'faculty';", nativeQuery = true)
    List<User> getAllFacultyDetails();

    //Getting faculty emails only
    @Query(value="SELECT email FROM user WHERE employee_type='faculty'",nativeQuery = true)
    List<String> getFacultyEmails();

    //Getting faculty emails that are not Part Time
    @Query(value="SELECT email FROM user WHERE employee_type = 'faculty' AND designation != 'Part Time'",nativeQuery = true)
    List<String> getRegularFacultyEmails();

    //Getting faculty emails that are Part Time
    @Query(value="SELECT email FROM user WHERE employee_type = 'faculty' AND designation = 'Part Time'",nativeQuery = true)
    List<String> getPartTimeFacultyEmails();

    //Getting all the details of STAFF
    @Query(value="SELECT * FROM user WHERE employee_type='staff';",nativeQuery = true)
    List<User> getAllStaffDetails();

    //Getting staff emails only;
    @Query(value="SELECT email FROM user WHERE employee_type='staff'",nativeQuery = true)
    List<String> getStaffEmails();

    //Getting staff emails that are not Job Order
    @Query(value="SELECT email FROM user WHERE employee_type='staff' AND designation != 'Job Order'",nativeQuery = true)
    List<String> getRegularStaffEmails();

    //Getting staff emails that are Job Orders
    @Query(value="SELECT email FROM user where employee_type='staff' AND designation = 'Job Order'",nativeQuery = true)
    List<String> getJobOrderStaffEmails();

    //Get all user info
//    @Query(value="SELECT * FROM user WHERE email IS NOT NULL",nativeQuery = true)
//    List<User> getAllUser();
    @Query(value="SELECT * FROM user",nativeQuery = true)
    List<User> getAllUser();

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password AND u.accessLevel IN (:accessLevels)")
    User findByUsernameAndPasswordAndAccessLevel(@Param("username") String username,
                                                 @Param("password") String password,
                                                 @Param("accessLevels") List<Integer> accessLevels);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET name = :name, designation = :designation, department_id = :departmentId, campus = :campus, company_name = :companyName, access_level = :accessLevel, employee_type = :employeeType, permanent = :permanent, email_receiver = :emailReceiver, email = :email WHERE user_id = :userId", nativeQuery = true)
    void updateUserDetails(
            @Param("name") String name,
            @Param("designation") String designation,
            @Param("departmentId") Long departmentId,
            @Param("campus") Long campus,
            @Param("companyName") String companyName,
            @Param("accessLevel") Long accessLevel,
            @Param("employeeType") String employeeType,
            @Param("permanent") Long permanent,
            @Param("emailReceiver") Long emailReceiver,
            @Param("email") String email,
            @Param("userId") Long userId
    );


}
