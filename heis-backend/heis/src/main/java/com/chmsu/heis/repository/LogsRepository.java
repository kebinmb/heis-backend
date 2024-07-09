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

@Repository
public interface LogsRepository extends JpaRepository<Logs,Long> {
    @Query(value = "SELECT logs.*\n" +
            "FROM logs\n" +
            "INNER JOIN user ON user.user_id = logs.user_id\n" +
            "WHERE DATE(logs.timestamp) = :date\n" +
            "  AND user.campus = :campus\n" +
            "  AND logs.message LIKE :keyword\n" +
            "ORDER BY logs.timestamp;\n",nativeQuery = true)
    Logs getLogs(@Param("date")String date,
                 @Param("campus")Integer campus,
                 @Param("keyword")String keyword);


    @Modifying
    @Transactional
    @Query(value="INSERT INTO logs (user_id,message,timestamp) VALUES (:userId,:message,:timestamp)",nativeQuery = true)
    void insertLogs(
                    @Param("userId")Integer userId,
                    @Param("message")String message,
                    @Param("timestamp")Date date);
}


