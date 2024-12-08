package com.rudbmsgroupproject.project_2.repository;

import com.rudbmsgroupproject.project_2.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    // Custom query to find applications where action_taken = 1
    List<Application> findByActionTaken(Short actionTaken);

    // Example query with @Query annotation
    @Query("SELECT a FROM Application a WHERE a.actionTaken = :actionTaken")
    List<Application> findApplicationsByActionTaken(@Param("actionTaken") Short actionTaken);

    // Example with pagination
    List<Application> findByActionTaken(Short actionTaken, Pageable pageable);
}