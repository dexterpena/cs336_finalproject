package com.rudbmsgroupproject.project_2.dto;

public class CreateMortgageRequest {
    private Integer applicantIncome000s;
    private Integer loanAmount000s;
    private Integer msamd;
    private Integer applicantSex;
    private Integer applicantEthnicity;
    private Integer loanType;

    public Integer getApplicantIncome000s() {
        return applicantIncome000s;
    }

    public void setApplicantIncome000s(Integer applicantIncome000s) {
        this.applicantIncome000s = applicantIncome000s;
    }

    public Integer getLoanAmount000s() {
        return loanAmount000s;
    }

    public void setLoanAmount000s(Integer loanAmount000s) {
        this.loanAmount000s = loanAmount000s;
    }

    public Integer getMsamd() {
        return msamd;
    }

    public void setMsamd(Integer msamd) {
        this.msamd = msamd;
    }

    public Integer getApplicantSex() {
        return applicantSex;
    }

    public void setApplicantSex(Integer applicantSex) {
        this.applicantSex = applicantSex;
    }

    public Integer getApplicantEthnicity() {
        return applicantEthnicity;
    }

    public void setApplicantEthnicity(Integer applicantEthnicity) {
        this.applicantEthnicity = applicantEthnicity;
    }

    public Integer getLoanType() {
        return loanType;
    }

    public void setLoanType(Integer loanType) {
        this.loanType = loanType;
    }
}
