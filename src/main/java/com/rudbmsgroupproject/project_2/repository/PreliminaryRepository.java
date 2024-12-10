package com.rudbmsgroupproject.project_2.repository;

import com.rudbmsgroupproject.project_2.model.Preliminary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreliminaryRepository extends JpaRepository<Preliminary, Integer> {

    @Query("SELECT p FROM Preliminary p WHERE p.actionTaken = 1")
    List<Preliminary> findAllByActionTaken();

    @Query("SELECT p FROM Preliminary p WHERE p.actionTaken = 1 AND " +
            "(:msamd IS NULL OR p.msamd IN :msamd) AND " +
            "(:minIncomeDebtRatio IS NULL OR :maxIncomeDebtRatio IS NULL OR " +
            "(p.applicantIncome000s / p.loanAmount000s) BETWEEN :minIncomeDebtRatio AND :maxIncomeDebtRatio) AND " +
            "(:counties IS NULL OR p.countyCode IN :counties) AND " +
            "(:loanTypes IS NULL OR p.loanType IN :loanTypes) AND " +
            "(:minTractIncome IS NULL OR p.tractToMsamdIncome >= :minTractIncome) AND " +
            "(:maxTractIncome IS NULL OR p.tractToMsamdIncome <= :maxTractIncome) AND " +
            "(:loanPurposes IS NULL OR p.loanPurpose IN :loanPurposes) AND " +
            "(:propertyTypes IS NULL OR p.propertyType IN :propertyTypes) AND " +
            "(:ownerOccupancy IS NULL OR p.ownerOccupancy = :ownerOccupancy) " +
            "ORDER BY p.applicationId ASC")
    List<Preliminary> findFilteredPreliminaries(
            @Param("msamd") List<Integer> msamd,
            @Param("minIncomeDebtRatio") Double minIncomeDebtRatio,
            @Param("maxIncomeDebtRatio") Double maxIncomeDebtRatio,
            @Param("counties") List<Integer> counties,
            @Param("loanTypes") List<Integer> loanTypes,
            @Param("minTractIncome") Double minTractIncome,
            @Param("maxTractIncome") Double maxTractIncome,
            @Param("loanPurposes") List<Integer> loanPurposes,
            @Param("propertyTypes") List<Integer> propertyTypes,
            @Param("ownerOccupancy") Integer ownerOccupancy);
}