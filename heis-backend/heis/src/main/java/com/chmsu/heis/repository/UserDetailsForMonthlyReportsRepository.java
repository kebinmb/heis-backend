package com.chmsu.heis.repository;

import com.chmsu.heis.model.document.UserDetailsForMonthlyReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailsForMonthlyReportsRepository extends JpaRepository<UserDetailsForMonthlyReports,Long> {


    @Query(value="SELECT * FROM USER",nativeQuery = true)
    List<UserDetailsForMonthlyReports> getReceiverName();
}
