package com.rudbmsgroupproject.project_2.dto;

import com.rudbmsgroupproject.project_2.model.Preliminary;
import java.util.List;

public class MortgageResponse {
    private int count;
    private int totalPages;
    private int currentPage;
    private int loanAmountSum;
    private List<Preliminary> data;

    public MortgageResponse(int count, int totalPages, int currentPage, int loanAmountSum, List<Preliminary> data) {
        this.count = count;
        this.loanAmountSum = loanAmountSum;
        this.data = data;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    // Getters
    public int getCount() {
        return count;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getLoanAmountSum() {
        return loanAmountSum;
    }

    public List<Preliminary> getData() {
        return data;
    }
}
