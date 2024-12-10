package com.rudbmsgroupproject.project_2.controller;

import com.rudbmsgroupproject.project_2.dto.CreateMortgageRequest;
import com.rudbmsgroupproject.project_2.dto.MortgageResponse;
import com.rudbmsgroupproject.project_2.model.Preliminary;
import com.rudbmsgroupproject.project_2.service.DatabaseService;
import com.rudbmsgroupproject.project_2.service.PreliminaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PreliminaryController {

    @Autowired
    private PreliminaryService preliminaryService;

    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/options/msamd")
    public List<String> getMsamdList() {
        return databaseService.getMsamdList();
    }

    @GetMapping("/options/counties")
    public List<String> getCountyList() {
        return databaseService.getCountyList();
    }

    @GetMapping("/options/loanTypes")
    public List<String> getLoanTypeList() {
        return databaseService.getLoanTypeList();
    }

    @GetMapping("/options/loanPurposes")
    public List<String> getLoanPurposeList() {
        return databaseService.getLoanPurposeList();
    }

    @GetMapping("/options/propertyTypes")
    public List<String> getPropertyTypeList() {
        return databaseService.getPropertyTypeList();
    }

    @GetMapping("/mortgages")
    public MortgageResponse getMortgages(
            @RequestParam(required = false) List<Integer> msamd,
            @RequestParam(required = false) Double minIncomeDebtRatio,
            @RequestParam(required = false) Double maxIncomeDebtRatio,
            @RequestParam(required = false) List<Integer> counties,
            @RequestParam(required = false) List<Integer> loanTypes,
            @RequestParam(required = false) Double minTractIncome,
            @RequestParam(required = false) Double maxTractIncome,
            @RequestParam(required = false) List<Integer> loanPurposes,
            @RequestParam(required = false) List<Integer> propertyTypes,
            @RequestParam(required = false) Integer ownerOccupancy,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1000") int size) {

        List<Integer> msamdIds = null;
        if (msamd != null && !msamd.isEmpty()) {
            List<String> msamdList = databaseService.getMsamdList();
            msamdIds = msamd.stream()
                    .filter(i -> i > 0 && i <= msamdList.size())
                    .map(i -> databaseService.getMsamdIdByName(msamdList.get(i - 1)))
                    .collect(Collectors.toList());
        }

        List<Integer> countyIds = null;
        if (counties != null && !counties.isEmpty()) {
            List<String> countyList = databaseService.getCountyList();
            countyIds = counties.stream()
                    .filter(i -> i > 0 && i <= countyList.size())
                    .map(i -> databaseService.getCountyIdByName(countyList.get(i - 1)))
                    .collect(Collectors.toList());
        }

        List<Integer> loanTypeIds = null;
        if (loanTypes != null && !loanTypes.isEmpty()) {
            List<String> loanTypeList = databaseService.getLoanTypeList();
            loanTypeIds = loanTypes.stream()
                    .filter(i -> i > 0 && i <= loanTypeList.size())
                    .map(i -> databaseService.getLoanTypeIdByName(loanTypeList.get(i - 1)))
                    .collect(Collectors.toList());
        }

        List<Integer> loanPurposeIds = null;
        if (loanPurposes != null && !loanPurposes.isEmpty()) {
            List<String> loanPurposeList = databaseService.getLoanPurposeList();
            loanPurposeIds = loanPurposes.stream()
                    .filter(i -> i > 0 && i <= loanPurposeList.size())
                    .map(i -> databaseService.getLoanPurposeIdByName(loanPurposeList.get(i - 1)))
                    .collect(Collectors.toList());
        }

        List<Integer> propertyTypeIds = null;
        if (propertyTypes != null && !propertyTypes.isEmpty()) {
            List<String> propertyTypeList = databaseService.getPropertyTypeList();
            propertyTypeIds = propertyTypes.stream()
                    .filter(i -> i > 0 && i <= propertyTypeList.size())
                    .map(i -> databaseService.getPropertyTypeIdByName(propertyTypeList.get(i - 1)))
                    .collect(Collectors.toList());
        }

        // Get filtered list using converted IDs
        List<Preliminary> allPreliminaries = preliminaryService.getFilteredPreliminaries(
                msamdIds,
                minIncomeDebtRatio,
                maxIncomeDebtRatio,
                countyIds,
                loanTypeIds,
                minTractIncome,
                maxTractIncome,
                loanPurposeIds,
                propertyTypeIds,
                ownerOccupancy);

        int totalCount = allPreliminaries.size();
        int totalLoanAmountSum = allPreliminaries.stream()
                .mapToInt(preliminary -> preliminary.getLoanAmount000s() != null ? preliminary.getLoanAmount000s() : 0)
                .sum();

        int totalPages = (int) Math.ceil((double) totalCount / size);

        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, totalCount);

        List<Preliminary> paginatedPreliminaries = startIndex < totalCount
                ? allPreliminaries.subList(startIndex, endIndex)
                : Collections.emptyList();

        return new MortgageResponse(totalCount, totalPages, page, totalLoanAmountSum, paginatedPreliminaries);
    }

    @PostMapping("/create-mortgage")
    public ResponseEntity<?> createMortgage(@RequestBody CreateMortgageRequest request) {
        try {

            List<String> msamdList = databaseService.getMsamdList();
            if (request.getMsamd() < 1 || request.getMsamd() > msamdList.size()) {
                return ResponseEntity.badRequest().body("Invalid MSAMD index");
            }
            Integer msamdId = databaseService.getMsamdIdByName(msamdList.get(request.getMsamd() - 1));
            String msamdName = msamdList.get(request.getMsamd() - 1);

            List<String> loanTypeList = databaseService.getLoanTypeList();
            if (request.getLoanType() < 1 || request.getLoanType() > loanTypeList.size()) {
                return ResponseEntity.badRequest().body("Invalid Loan Type index");
            }
            Integer loanTypeId = databaseService.getLoanTypeIdByName(loanTypeList.get(request.getLoanType() - 1));
            String loanTypeName = loanTypeList.get(request.getLoanType() - 1);

            String applicantSexName = databaseService.getApplicantSexNameById(request.getApplicantSex());
            String applicantEthnicityName = databaseService
                    .getApplicantEthnicityNameById(request.getApplicantEthnicity());

            // Create new Preliminary object
            Preliminary preliminary = new Preliminary();
            preliminary.setApplicationId(null); // Auto-generated
            preliminary.setApplicantIncome000s(request.getApplicantIncome000s());
            preliminary.setLoanAmount000s(request.getLoanAmount000s());
            preliminary.setMsamd(msamdId);
            preliminary.setMsamdName(msamdName);
            preliminary.setApplicantSex(request.getApplicantSex());
            preliminary.setApplicantSexName(applicantSexName);
            preliminary.setApplicantEthnicity(request.getApplicantEthnicity());
            preliminary.setApplicantEthnicityName(applicantEthnicityName);
            preliminary.setLoanType(loanTypeId);
            preliminary.setLoanTypeName(loanTypeName);
            preliminary.setActionTaken((short) 1);

            // Save to database
            preliminaryService.createMortgage(preliminary);

            return ResponseEntity.ok().body("Mortgage created successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating mortgage: " + e.getMessage());
        }
    }

    @GetMapping("/calculate-rate")
    public ResponseEntity<?> calculateRate(
            @RequestParam(required = false) List<Integer> msamd,
            @RequestParam(required = false) Double minIncomeDebtRatio,
            @RequestParam(required = false) Double maxIncomeDebtRatio,
            @RequestParam(required = false) List<Integer> counties,
            @RequestParam(required = false) List<Integer> loanTypes,
            @RequestParam(required = false) Double minTractIncome,
            @RequestParam(required = false) Double maxTractIncome,
            @RequestParam(required = false) List<Integer> loanPurposes,
            @RequestParam(required = false) List<Integer> propertyTypes,
            @RequestParam(required = false) Integer ownerOccupancy) {
        try {
            // Convert indices to IDs (reuse existing conversion logic)
            List<Integer> msamdIds = null;
            if (msamd != null && !msamd.isEmpty()) {
                List<String> msamdList = databaseService.getMsamdList();
                msamdIds = msamd.stream()
                        .filter(i -> i > 0 && i <= msamdList.size())
                        .map(i -> databaseService.getMsamdIdByName(msamdList.get(i - 1)))
                        .collect(Collectors.toList());
            }

            List<Integer> countyIds = null;
            if (counties != null && !counties.isEmpty()) {
                List<String> countyList = databaseService.getCountyList();
                countyIds = counties.stream()
                        .filter(i -> i > 0 && i <= countyList.size())
                        .map(i -> databaseService.getCountyIdByName(countyList.get(i - 1)))
                        .collect(Collectors.toList());
            }

            List<Integer> loanTypeIds = null;
            if (loanTypes != null && !loanTypes.isEmpty()) {
                List<String> loanTypeList = databaseService.getLoanTypeList();
                loanTypeIds = loanTypes.stream()
                        .filter(i -> i > 0 && i <= loanTypeList.size())
                        .map(i -> databaseService.getLoanTypeIdByName(loanTypeList.get(i - 1)))
                        .collect(Collectors.toList());
            }

            List<Integer> loanPurposeIds = null;
            if (loanPurposes != null && !loanPurposes.isEmpty()) {
                List<String> loanPurposeList = databaseService.getLoanPurposeList();
                loanPurposeIds = loanPurposes.stream()
                        .filter(i -> i > 0 && i <= loanPurposeList.size())
                        .map(i -> databaseService.getLoanPurposeIdByName(loanPurposeList.get(i - 1)))
                        .collect(Collectors.toList());
            }

            List<Integer> propertyTypeIds = null;
            if (propertyTypes != null && !propertyTypes.isEmpty()) {
                List<String> propertyTypeList = databaseService.getPropertyTypeList();
                propertyTypeIds = propertyTypes.stream()
                        .filter(i -> i > 0 && i <= propertyTypeList.size())
                        .map(i -> databaseService.getPropertyTypeIdByName(propertyTypeList.get(i - 1)))
                        .collect(Collectors.toList());
            }

            // Get filtered mortgages
            List<Preliminary> preliminaries = preliminaryService.getFilteredPreliminaries(
                    msamdIds, minIncomeDebtRatio, maxIncomeDebtRatio,
                    countyIds, loanTypeIds, minTractIncome, maxTractIncome,
                    loanPurposeIds, propertyTypeIds, ownerOccupancy);

            if (preliminaries.isEmpty()) {
                return ResponseEntity.badRequest().body("No mortgages found matching criteria");
            }

            double baseRate = 2.33;
            double totalWeightedRate = 0;
            double totalLoanAmount = 0;

            // Calculate weighted average rate
            for (Preliminary p : preliminaries) {
                if (p.getLoanAmount000s() == null)
                    continue;

                double rateSpread;
                if (p.getRateSpread() != null && p.getRateSpread() > 0) {
                    rateSpread = p.getRateSpread();
                } else {
                    // Use maximum values based on lien status
                    int lienStatus = p.getLienStatus() != null ? p.getLienStatus() : 0;

                    if (lienStatus == 1) {
                        rateSpread = 1.5;
                    } else if (lienStatus == 2) {
                        rateSpread = 3.5;
                    } else {
                        continue; // Skip if lien status is unknown
                    }
                }

                double rate = baseRate + rateSpread;
                totalWeightedRate += rate * p.getLoanAmount000s();
                totalLoanAmount += p.getLoanAmount000s();
            }

            if (totalLoanAmount == 0) {
                return ResponseEntity.badRequest().body("No valid loan amounts found");
            }

            double weightedAverageRate = totalWeightedRate / totalLoanAmount;

            Map<String, Object> response = new HashMap<>();
            response.put("rate", weightedAverageRate);
            response.put("cost", totalLoanAmount);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error calculating rate: " + e.getMessage());
        }
    }

    @GetMapping("/accept-rate")
    public ResponseEntity<?> acceptRate(
            @RequestParam(required = false) List<Integer> msamd,
            @RequestParam(required = false) Double minIncomeDebtRatio,
            @RequestParam(required = false) Double maxIncomeDebtRatio,
            @RequestParam(required = false) List<Integer> counties,
            @RequestParam(required = false) List<Integer> loanTypes,
            @RequestParam(required = false) Double minTractIncome,
            @RequestParam(required = false) Double maxTractIncome,
            @RequestParam(required = false) List<Integer> loanPurposes,
            @RequestParam(required = false) List<Integer> propertyTypes,
            @RequestParam(required = false) Integer ownerOccupancy) {
        try {
            List<Integer> msamdIds = null;
            if (msamd != null && !msamd.isEmpty()) {
                List<String> msamdList = databaseService.getMsamdList();
                msamdIds = msamd.stream()
                        .filter(i -> i > 0 && i <= msamdList.size())
                        .map(i -> databaseService.getMsamdIdByName(msamdList.get(i - 1)))
                        .collect(Collectors.toList());
            }

            List<Integer> countyIds = null;
            if (counties != null && !counties.isEmpty()) {
                List<String> countyList = databaseService.getCountyList();
                countyIds = counties.stream()
                        .filter(i -> i > 0 && i <= countyList.size())
                        .map(i -> databaseService.getCountyIdByName(countyList.get(i - 1)))
                        .collect(Collectors.toList());
            }

            List<Integer> loanTypeIds = null;
            if (loanTypes != null && !loanTypes.isEmpty()) {
                List<String> loanTypeList = databaseService.getLoanTypeList();
                loanTypeIds = loanTypes.stream()
                        .filter(i -> i > 0 && i <= loanTypeList.size())
                        .map(i -> databaseService.getLoanTypeIdByName(loanTypeList.get(i - 1)))
                        .collect(Collectors.toList());
            }

            List<Integer> loanPurposeIds = null;
            if (loanPurposes != null && !loanPurposes.isEmpty()) {
                List<String> loanPurposeList = databaseService.getLoanPurposeList();
                loanPurposeIds = loanPurposes.stream()
                        .filter(i -> i > 0 && i <= loanPurposeList.size())
                        .map(i -> databaseService.getLoanPurposeIdByName(loanPurposeList.get(i - 1)))
                        .collect(Collectors.toList());
            }

            List<Integer> propertyTypeIds = null;
            if (propertyTypes != null && !propertyTypes.isEmpty()) {
                List<String> propertyTypeList = databaseService.getPropertyTypeList();
                propertyTypeIds = propertyTypes.stream()
                        .filter(i -> i > 0 && i <= propertyTypeList.size())
                        .map(i -> databaseService.getPropertyTypeIdByName(propertyTypeList.get(i - 1)))
                        .collect(Collectors.toList());
            }

            // Get filtered mortgages
            List<Preliminary> preliminaries = preliminaryService.getFilteredPreliminaries(
                    msamdIds, minIncomeDebtRatio, maxIncomeDebtRatio,
                    countyIds, loanTypeIds, minTractIncome, maxTractIncome,
                    loanPurposeIds, propertyTypeIds, ownerOccupancy);

            if (preliminaries.isEmpty()) {
                return ResponseEntity.badRequest().body("No mortgages found matching criteria");
            }

            // Update purchaser type for all matched mortgages
            for (Preliminary p : preliminaries) {
                p.setPurchaserType(5);
                p.setPurchaserTypeName("Private securitization");
                preliminaryService.saveMortgage(p);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Successfully updated " + preliminaries.size() + " mortgages");
            response.put("count", preliminaries.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error accepting rate: " + e.getMessage());
        }
    }
}
