package com.chmsu.heis.repository;

import com.chmsu.heis.model.document.Archive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive,Integer > {
    @Query(value="SELECT * from document;",nativeQuery = true)
    List<Archive> getAllDocuments();

}
