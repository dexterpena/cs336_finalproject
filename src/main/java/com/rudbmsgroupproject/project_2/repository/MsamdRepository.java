package com.rudbmsgroupproject.project_2.repository;

import com.rudbmsgroupproject.project_2.model.Msamd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MsamdRepository extends JpaRepository<Msamd, Integer> {
}