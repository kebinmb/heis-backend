package com.chmsu.heis.repository;

import com.chmsu.heis.model.document.User;
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password AND u.accessLevel IN (:accessLevels)")
    User findByUsernameAndPasswordAndAccessLevel(@Param("username") String username,
                                                 @Param("password") String password,
                                                 @Param("accessLevels") List<Integer> accessLevels);
}
