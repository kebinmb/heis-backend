package com.chmsu.heis.repository;

import com.chmsu.heis.model.document.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository {

    @Query(value = "",nativeQuery = true)
    User login();

}
