package com.rudbmsgroupproject.project_2.service;

import com.rudbmsgroupproject.project_2.model.Preliminary;
import com.rudbmsgroupproject.project_2.repository.PreliminaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreliminaryService {

    @Autowired
    private PreliminaryRepository preliminaryRepository;

    public List<Preliminary> getAllByActionTaken() {
        return preliminaryRepository.findAllByActionTaken();
    }

    public List<Preliminary> getFilteredPreliminaries(
            List<Integer> msamd,
            Double minIncomeDebtRatio,
            Double maxIncomeDebtRatio,
            List<Integer> counties,
            List<Short> loanTypes,
            Double minTractIncome,
            Double maxTractIncome,
            List<Short> loanPurposes,
            List<Short> propertyTypes,
            Short ownerOccupancy) {
        return preliminaryRepository.findFilteredPreliminaries(msamd, minIncomeDebtRatio, maxIncomeDebtRatio, counties, loanTypes, minTractIncome, maxTractIncome, loanPurposes, propertyTypes, ownerOccupancy);
    }
}