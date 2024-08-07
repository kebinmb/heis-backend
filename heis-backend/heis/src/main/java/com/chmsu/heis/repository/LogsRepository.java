package com.chmsu.heis.repository;


import com.chmsu.heis.model.document.Logs;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface LogsRepository extends JpaRepository<Logs,Long> {
    @Query(value = "SELECT logs.* " +
            "FROM logs " +
            "INNER JOIN user ON user.user_id = logs.user_id " +
            "WHERE DATE(logs.timestamp) = :date " +
            " AND user.campus = :campus " +
            "AND (message LIKE '%Login Successful%' OR message LIKE '%authenticated%')"+
            "ORDER BY logs.timestamp;", nativeQuery = true)
    List<Logs> getLogs(@Param("date") String date, @Param("campus") Integer campus);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO logs (user_id,message,timestamp) VALUES (:userId,:message,:timestamp)",nativeQuery = true)
    void insertLogs(
                    @Param("userId")Long userId,
                    @Param("message")String message,
                    @Param("timestamp")Date date);
}


