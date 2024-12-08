package com.rudbmsgroupproject.project_2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Integer applicationId;

    @Column(name = "as_of_year")
    private Integer asOfYear;

    @Column(name = "respondent_id")
    private String respondentId;

    @Column(name = "agency_code")
    private Short agencyCode;

    @Column(name = "loan_type")
    private Short loanType;

    @Column(name = "property_type")
    private Short propertyType;

    @Column(name = "loan_purpose")
    private Short loanPurpose;

    @Column(name = "owner_occupancy")
    private Short ownerOccupancy;

    @Column(name = "loan_amount_000s")
    private Double loanAmount000s;

    @Column(name = "preapproval")
    private Short preapproval;

    @Column(name = "action_taken")
    private Short actionTaken;

    @Column(name = "applicant_ethnicity")
    private Short applicantEthnicity;

    @Column(name = "applicant_sex")
    private Short applicantSex;

    @Column(name = "applicant_income_000s")
    private Double applicantIncome000s;

    @Column(name = "co_applicant_ethnicity")
    private Short coApplicantEthnicity;

    @Column(name = "co_applicant_sex")
    private Short coApplicantSex;

    @Column(name = "applicant_race_1")
    private Short applicantRace1;

    @Column(name = "co_applicant_race_1")
    private Short coApplicantRace1;

    @Column(name = "purchaser_type")
    private Short purchaserType;

    @Column(name = "rate_spread")
    private Double rateSpread;

    @Column(name = "hoepa_status")
    private Short hoepaStatus;

    @Column(name = "lien_status")
    private Short lienStatus;

    @Column(name = "location_code")
    private Integer locationCode;

    // Getters and Setters (can be generated automatically)
    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getAsOfYear() {
        return asOfYear;
    }

    public void setAsOfYear(Integer asOfYear) {
        this.asOfYear = asOfYear;
    }

    public String getRespondentId() {
        return respondentId;
    }

    public void setRespondentId(String respondentId) {
        this.respondentId = respondentId;
    }

    public Short getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(Short agencyCode) {
        this.agencyCode = agencyCode;
    }

    public Short getLoanType() {
        return loanType;
    }

    public void setLoanType(Short loanType) {
        this.loanType = loanType;
    }

    public Short getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Short propertyType) {
        this.propertyType = propertyType;
    }

    public Short getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(Short loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public Short getOwnerOccupancy() {
        return ownerOccupancy;
    }

    public void setOwnerOccupancy(Short ownerOccupancy) {
        this.ownerOccupancy = ownerOccupancy;
    }

    public Double getLoanAmount000s() {
        return loanAmount000s;
    }

    public void setLoanAmount000s(Double loanAmount000s) {
        this.loanAmount000s = loanAmount000s;
    }

    public Short getPreapproval() {
        return preapproval;
    }

    public void setPreapproval(Short preapproval) {
        this.preapproval = preapproval;
    }

    public Short getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(Short actionTaken) {
        this.actionTaken = actionTaken;
    }

    public Short getApplicantEthnicity() {
        return applicantEthnicity;
    }

    public void setApplicantEthnicity(Short applicantEthnicity) {
        this.applicantEthnicity = applicantEthnicity;
    }

    public Short getApplicantSex() {
        return applicantSex;
    }

    public void setApplicantSex(Short applicantSex) {
        this.applicantSex = applicantSex;
    }

    public Double getApplicantIncome000s() {
        return applicantIncome000s;
    }

    public void setApplicantIncome000s(Double applicantIncome000s) {
        this.applicantIncome000s = applicantIncome000s;
    }

    public Short getCoApplicantEthnicity() {
        return coApplicantEthnicity;
    }

    public void setCoApplicantEthnicity(Short coApplicantEthnicity) {
        this.coApplicantEthnicity = coApplicantEthnicity;
    }

    public Short getCoApplicantSex() {
        return coApplicantSex;
    }

    public void setCoApplicantSex(Short coApplicantSex) {
        this.coApplicantSex = coApplicantSex;
    }

    public Short getApplicantRace1() {
        return applicantRace1;
    }

    public void setApplicantRace1(Short applicantRace1) {
        this.applicantRace1 = applicantRace1;
    }

    public Short getCoApplicantRace1() {
        return coApplicantRace1;
    }

    public void setCoApplicantRace1(Short coApplicantRace1) {
        this.coApplicantRace1 = coApplicantRace1;
    }

    public Short getPurchaserType() {
        return purchaserType;
    }

    public void setPurchaserType(Short purchaserType) {
        this.purchaserType = purchaserType;
    }

    public Double getRateSpread() {
        return rateSpread;
    }

    public void setRateSpread(Double rateSpread) {
        this.rateSpread = rateSpread;
    }

    public Short getHoepaStatus() {
        return hoepaStatus;
    }

    public void setHoepaStatus(Short hoepaStatus) {
        this.hoepaStatus = hoepaStatus;
    }

    public Short getLienStatus() {
        return lienStatus;
    }

    public void setLienStatus(Short lienStatus) {
        this.lienStatus = lienStatus;
    }

    public Integer getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(Integer locationCode) {
        this.locationCode = locationCode;
    }
}