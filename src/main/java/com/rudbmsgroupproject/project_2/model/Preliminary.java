package com.rudbmsgroupproject.project_2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "preliminary")
public class Preliminary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Integer applicationId;

    @Column(name = "as_of_year")
    private Integer asOfYear;

    @Column(name = "respondent_id")
    private String respondentId;

    @Column(name = "agency_name")
    private String agencyName;

    @Column(name = "agency_abbr")
    private String agencyAbbr;

    @Column(name = "agency_code")
    private Short agencyCode;

    @Column(name = "loan_type_name")
    private String loanTypeName;

    @Column(name = "loan_type")
    private Short loanType;

    @Column(name = "property_type_name")
    private String propertyTypeName;

    @Column(name = "property_type")
    private Short propertyType;

    @Column(name = "loan_purpose_name")
    private String loanPurposeName;

    @Column(name = "loan_purpose")
    private Short loanPurpose;

    @Column(name = "owner_occupancy_name")
    private String ownerOccupancyName;

    @Column(name = "owner_occupancy")
    private Short ownerOccupancy;

    @Column(name = "loan_amount_000s")
    private Integer loanAmount000s;

    @Column(name = "preapproval_name")
    private String preapprovalName;

    @Column(name = "preapproval")
    private Short preapproval;

    @Column(name = "action_taken_name")
    private String actionTakenName;

    @Column(name = "action_taken")
    private Short actionTaken;

    @Column(name = "msamd_name")
    private String msamdName;

    @Column(name = "msamd")
    private Integer msamd;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "state_abbr")
    private String stateAbbr;

    @Column(name = "state_code")
    private Short stateCode;

    @Column(name = "county_name")
    private String countyName;

    @Column(name = "county_code")
    private Short countyCode;

    @Column(name = "census_tract_number")
    private Double censusTractNumber;

    @Column(name = "applicant_ethnicity_name")
    private String applicantEthnicityName;

    @Column(name = "applicant_ethnicity")
    private Short applicantEthnicity;

    @Column(name = "co_applicant_ethnicity_name")
    private String coApplicantEthnicityName;

    @Column(name = "co_applicant_ethnicity")
    private Short coApplicantEthnicity;

    @Column(name = "applicant_race_name_1")
    private String applicantRaceName1;

    @Column(name = "applicant_race_1")
    private Short applicantRace1;

    @Column(name = "applicant_race_name_2")
    private String applicantRaceName2;

    @Column(name = "applicant_race_2")
    private Short applicantRace2;

    @Column(name = "applicant_race_name_3")
    private String applicantRaceName3;

    @Column(name = "applicant_race_3")
    private Short applicantRace3;

    @Column(name = "applicant_race_name_4")
    private String applicantRaceName4;

    @Column(name = "applicant_race_4")
    private Short applicantRace4;

    @Column(name = "applicant_race_name_5")
    private String applicantRaceName5;

    @Column(name = "applicant_race_5")
    private Short applicantRace5;

    @Column(name = "co_applicant_race_name_1")
    private String coApplicantRaceName1;

    @Column(name = "co_applicant_race_1")
    private Short coApplicantRace1;

    @Column(name = "co_applicant_race_name_2")
    private String coApplicantRaceName2;

    @Column(name = "co_applicant_race_2")
    private Short coApplicantRace2;

    @Column(name = "co_applicant_race_name_3")
    private String coApplicantRaceName3;

    @Column(name = "co_applicant_race_3")
    private Short coApplicantRace3;

    @Column(name = "co_applicant_race_name_4")
    private String coApplicantRaceName4;

    @Column(name = "co_applicant_race_4")
    private Short coApplicantRace4;

    @Column(name = "co_applicant_race_name_5")
    private String coApplicantRaceName5;

    @Column(name = "co_applicant_race_5")
    private Short coApplicantRace5;

    @Column(name = "applicant_sex_name")
    private String applicantSexName;

    @Column(name = "applicant_sex")
    private Short applicantSex;

    @Column(name = "co_applicant_sex_name")
    private String coApplicantSexName;

    @Column(name = "co_applicant_sex")
    private Short coApplicantSex;

    @Column(name = "applicant_income_000s")
    private Double applicantIncome000s;

    @Column(name = "purchaser_type_name")
    private String purchaserTypeName;

    @Column(name = "purchaser_type")
    private Short purchaserType;

    @Column(name = "denial_reason_name_1")
    private String denialReasonName1;

    @Column(name = "denial_reason_1")
    private Short denialReason1;

    @Column(name = "denial_reason_name_2")
    private String denialReasonName2;

    @Column(name = "denial_reason_2")
    private Short denialReason2;

    @Column(name = "denial_reason_name_3")
    private String denialReasonName3;

    @Column(name = "denial_reason_3")
    private Short denialReason3;

    @Column(name = "rate_spread")
    private Double rateSpread;

    @Column(name = "hoepa_status_name")
    private String hoepaStatusName;

    @Column(name = "hoepa_status")
    private Short hoepaStatus;

    @Column(name = "lien_status_name")
    private String lienStatusName;

    @Column(name = "lien_status")
    private Short lienStatus;

    @Column(name = "edit_status_name")
    private String editStatusName;

    @Column(name = "edit_status")
    private Short editStatus;

    @Column(name = "sequence_number")
    private Double sequenceNumber;

    @Column(name = "population")
    private Integer population;

    @Column(name = "minority_population")
    private Double minorityPopulation;

    @Column(name = "hud_median_family_income")
    private Integer hudMedianFamilyIncome;

    @Column(name = "tract_to_msamd_income")
    private Double tractToMsamdIncome;

    @Column(name = "number_of_owner_occupied_units")
    private Integer numberOfOwnerOccupiedUnits;

    @Column(name = "number_of_1_to_4_family_units")
    private Integer numberOf1To4FamilyUnits;

    @Column(name = "application_date_indicator")
    private Short applicationDateIndicator;

    @Column(name = "location_code")
    private Integer locationCode;

    // Getters and Setters

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

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyAbbr() {
        return agencyAbbr;
    }

    public void setAgencyAbbr(String agencyAbbr) {
        this.agencyAbbr = agencyAbbr;
    }

    public Short getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(Short agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getLoanTypeName() {
        return loanTypeName;
    }

    public void setLoanTypeName(String loanTypeName) {
        this.loanTypeName = loanTypeName;
    }

    public Short getLoanType() {
        return loanType;
    }

    public void setLoanType(Short loanType) {
        this.loanType = loanType;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public Short getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Short propertyType) {
        this.propertyType = propertyType;
    }

    public String getLoanPurposeName() {
        return loanPurposeName;
    }

    public void setLoanPurposeName(String loanPurposeName) {
        this.loanPurposeName = loanPurposeName;
    }

    public Short getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(Short loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public String getOwnerOccupancyName() {
        return ownerOccupancyName;
    }

    public void setOwnerOccupancyName(String ownerOccupancyName) {
        this.ownerOccupancyName = ownerOccupancyName;
    }

    public Short getOwnerOccupancy() {
        return ownerOccupancy;
    }

    public void setOwnerOccupancy(Short ownerOccupancy) {
        this.ownerOccupancy = ownerOccupancy;
    }

    public Integer getLoanAmount000s() {
        return loanAmount000s;
    }

    public void setLoanAmount000s(Integer loanAmount000s) {
        this.loanAmount000s = loanAmount000s;
    }

    public String getPreapprovalName() {
        return preapprovalName;
    }

    public void setPreapprovalName(String preapprovalName) {
        this.preapprovalName = preapprovalName;
    }

    public Short getPreapproval() {
        return preapproval;
    }

    public void setPreapproval(Short preapproval) {
        this.preapproval = preapproval;
    }

    public String getActionTakenName() {
        return actionTakenName;
    }

    public void setActionTakenName(String actionTakenName) {
        this.actionTakenName = actionTakenName;
    }

    public Short getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(Short actionTaken) {
        this.actionTaken = actionTaken;
    }

    public String getMsamdName() {
        return msamdName;
    }

    public void setMsamdName(String msamdName) {
        this.msamdName = msamdName;
    }

    public Integer getMsamd() {
        return msamd;
    }

    public void setMsamd(Integer msamd) {
        this.msamd = msamd;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateAbbr() {
        return stateAbbr;
    }

    public void setStateAbbr(String stateAbbr) {
        this.stateAbbr = stateAbbr;
    }

    public Short getStateCode() {
        return stateCode;
    }

    public void setStateCode(Short stateCode) {
        this.stateCode = stateCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public Short getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(Short countyCode) {
        this.countyCode = countyCode;
    }

    public Double getCensusTractNumber() {
        return censusTractNumber;
    }

    public void setCensusTractNumber(Double censusTractNumber) {
        this.censusTractNumber = censusTractNumber;
    }

    public String getApplicantEthnicityName() {
        return applicantEthnicityName;
    }

    public void setApplicantEthnicityName(String applicantEthnicityName) {
        this.applicantEthnicityName = applicantEthnicityName;
    }

    public Short getApplicantEthnicity() {
        return applicantEthnicity;
    }

    public void setApplicantEthnicity(Short applicantEthnicity) {
        this.applicantEthnicity = applicantEthnicity;
    }

    public String getCoApplicantEthnicityName() {
        return coApplicantEthnicityName;
    }

    public void setCoApplicantEthnicityName(String coApplicantEthnicityName) {
        this.coApplicantEthnicityName = coApplicantEthnicityName;
    }

    public Short getCoApplicantEthnicity() {
        return coApplicantEthnicity;
    }

    public void setCoApplicantEthnicity(Short coApplicantEthnicity) {
        this.coApplicantEthnicity = coApplicantEthnicity;
    }

    public String getApplicantRaceName1() {
        return applicantRaceName1;
    }

    public void setApplicantRaceName1(String applicantRaceName1) {
        this.applicantRaceName1 = applicantRaceName1;
    }

    public Short getApplicantRace1() {
        return applicantRace1;
    }

    public void setApplicantRace1(Short applicantRace1) {
        this.applicantRace1 = applicantRace1;
    }

    public String getApplicantRaceName2() {
        return applicantRaceName2;
    }

    public void setApplicantRaceName2(String applicantRaceName2) {
        this.applicantRaceName2 = applicantRaceName2;
    }

    public Short getApplicantRace2() {
        return applicantRace2;
    }

    public void setApplicantRace2(Short applicantRace2) {
        this.applicantRace2 = applicantRace2;
    }

    public String getApplicantRaceName3() {
        return applicantRaceName3;
    }

    public void setApplicantRaceName3(String applicantRaceName3) {
        this.applicantRaceName3 = applicantRaceName3;
    }

    public Short getApplicantRace3() {
        return applicantRace3;
    }

    public void setApplicantRace3(Short applicantRace3) {
        this.applicantRace3 = applicantRace3;
    }

    public String getApplicantRaceName4() {
        return applicantRaceName4;
    }

    public void setApplicantRaceName4(String applicantRaceName4) {
        this.applicantRaceName4 = applicantRaceName4;
    }

    public Short getApplicantRace4() {
        return applicantRace4;
    }

    public void setApplicantRace4(Short applicantRace4) {
        this.applicantRace4 = applicantRace4;
    }

    public String getApplicantRaceName5() {
        return applicantRaceName5;
    }

    public void setApplicantRaceName5(String applicantRaceName5) {
        this.applicantRaceName5 = applicantRaceName5;
    }

    public Short getApplicantRace5() {
        return applicantRace5;
    }

    public void setApplicantRace5(Short applicantRace5) {
        this.applicantRace5 = applicantRace5;
    }

    public String getCoApplicantRaceName1() {
        return coApplicantRaceName1;
    }

    public void setCoApplicantRaceName1(String coApplicantRaceName1) {
        this.coApplicantRaceName1 = coApplicantRaceName1;
    }

    public Short getCoApplicantRace1() {
        return coApplicantRace1;
    }

    public void setCoApplicantRace1(Short coApplicantRace1) {
        this.coApplicantRace1 = coApplicantRace1;
    }

    public String getCoApplicantRaceName2() {
        return coApplicantRaceName2;
    }

    public void setCoApplicantRaceName2(String coApplicantRaceName2) {
        this.coApplicantRaceName2 = coApplicantRaceName2;
    }

    public Short getCoApplicantRace2() {
        return coApplicantRace2;
    }

    public void setCoApplicantRace2(Short coApplicantRace2) {
        this.coApplicantRace2 = coApplicantRace2;
    }

    public String getCoApplicantRaceName3() {
        return coApplicantRaceName3;
    }

    public void setCoApplicantRaceName3(String coApplicantRaceName3) {
        this.coApplicantRaceName3 = coApplicantRaceName3;
    }

    public Short getCoApplicantRace3() {
        return coApplicantRace3;
    }

    public void setCoApplicantRace3(Short coApplicantRace3) {
        this.coApplicantRace3 = coApplicantRace3;
    }

    public String getCoApplicantRaceName4() {
        return coApplicantRaceName4;
    }

    public void setCoApplicantRaceName4(String coApplicantRaceName4) {
        this.coApplicantRaceName4 = coApplicantRaceName4;
    }

    public Short getCoApplicantRace4() {
        return coApplicantRace4;
    }

    public void setCoApplicantRace4(Short coApplicantRace4) {
        this.coApplicantRace4 = coApplicantRace4;
    }

    public String getCoApplicantRaceName5() {
        return coApplicantRaceName5;
    }

    public void setCoApplicantRaceName5(String coApplicantRaceName5) {
        this.coApplicantRaceName5 = coApplicantRaceName5;
    }

    public Short getCoApplicantRace5() {
        return coApplicantRace5;
    }

    public void setCoApplicantRace5(Short coApplicantRace5) {
        this.coApplicantRace5 = coApplicantRace5;
    }

    public String getApplicantSexName() {
        return applicantSexName;
    }

    public void setApplicantSexName(String applicantSexName) {
        this.applicantSexName = applicantSexName;
    }

    public Short getApplicantSex() {
        return applicantSex;
    }

    public void setApplicantSex(Short applicantSex) {
        this.applicantSex = applicantSex;
    }

    public String getCoApplicantSexName() {
        return coApplicantSexName;
    }

    public void setCoApplicantSexName(String coApplicantSexName) {
        this.coApplicantSexName = coApplicantSexName;
    }

    public Short getCoApplicantSex() {
        return coApplicantSex;
    }

    public void setCoApplicantSex(Short coApplicantSex) {
        this.coApplicantSex = coApplicantSex;
    }

    public Double getApplicantIncome000s() {
        return applicantIncome000s;
    }

    public void setApplicantIncome000s(Double applicantIncome000s) {
        this.applicantIncome000s = applicantIncome000s;
    }

    public String getPurchaserTypeName() {
        return purchaserTypeName;
    }

    public void setPurchaserTypeName(String purchaserTypeName) {
        this.purchaserTypeName = purchaserTypeName;
    }

    public Short getPurchaserType() {
        return purchaserType;
    }

    public void setPurchaserType(Short purchaserType) {
        this.purchaserType = purchaserType;
    }

    public String getDenialReasonName1() {
        return denialReasonName1;
    }

    public void setDenialReasonName1(String denialReasonName1) {
        this.denialReasonName1 = denialReasonName1;
    }

    public Short getDenialReason1() {
        return denialReason1;
    }

    public void setDenialReason1(Short denialReason1) {
        this.denialReason1 = denialReason1;
    }

    public String getDenialReasonName2() {
        return denialReasonName2;
    }

    public void setDenialReasonName2(String denialReasonName2) {
        this.denialReasonName2 = denialReasonName2;
    }

    public Short getDenialReason2() {
        return denialReason2;
    }

    public void setDenialReason2(Short denialReason2) {
        this.denialReason2 = denialReason2;
    }

    public String getDenialReasonName3() {
        return denialReasonName3;
    }

    public void setDenialReasonName3(String denialReasonName3) {
        this.denialReasonName3 = denialReasonName3;
    }

    public Short getDenialReason3() {
        return denialReason3;
    }

    public void setDenialReason3(Short denialReason3) {
        this.denialReason3 = denialReason3;
    }

    public Double getRateSpread() {
        return rateSpread;
    }

    public void setRateSpread(Double rateSpread) {
        this.rateSpread = rateSpread;
    }

    public String getHoepaStatusName() {
        return hoepaStatusName;
    }

    public void setHoepaStatusName(String hoepaStatusName) {
        this.hoepaStatusName = hoepaStatusName;
    }

    public Short getHoepaStatus() {
        return hoepaStatus;
    }

    public void setHoepaStatus(Short hoepaStatus) {
        this.hoepaStatus = hoepaStatus;
    }

    public String getLienStatusName() {
        return lienStatusName;
    }

    public void setLienStatusName(String lienStatusName) {
        this.lienStatusName = lienStatusName;
    }

    public Short getLienStatus() {
        return lienStatus;
    }

    public void setLienStatus(Short lienStatus) {
        this.lienStatus = lienStatus;
    }

    public String getEditStatusName() {
        return editStatusName;
    }

    public void setEditStatusName(String editStatusName) {
        this.editStatusName = editStatusName;
    }

    public Short getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Short editStatus) {
        this.editStatus = editStatus;
    }

    public Double getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Double sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Double getMinorityPopulation() {
        return minorityPopulation;
    }

    public void setMinorityPopulation(Double minorityPopulation) {
        this.minorityPopulation = minorityPopulation;
    }

    public Integer getHudMedianFamilyIncome() {
        return hudMedianFamilyIncome;
    }

    public void setHudMedianFamilyIncome(Integer hudMedianFamilyIncome) {
        this.hudMedianFamilyIncome = hudMedianFamilyIncome;
    }

    public Double getTractToMsamdIncome() {
        return tractToMsamdIncome;
    }

    public void setTractToMsamdIncome(Double tractToMsamdIncome) {
        this.tractToMsamdIncome = tractToMsamdIncome;
    }

    public Integer getNumberOfOwnerOccupiedUnits() {
        return numberOfOwnerOccupiedUnits;
    }

    public void setNumberOfOwnerOccupiedUnits(Integer numberOfOwnerOccupiedUnits) {
        this.numberOfOwnerOccupiedUnits = numberOfOwnerOccupiedUnits;
    }

    public Integer getNumberOf1To4FamilyUnits() {
        return numberOf1To4FamilyUnits;
    }

    public void setNumberOf1To4FamilyUnits(Integer numberOf1To4FamilyUnits) {
        this.numberOf1To4FamilyUnits = numberOf1To4FamilyUnits;
    }

    public Short getApplicationDateIndicator() {
        return applicationDateIndicator;
    }

    public void setApplicationDateIndicator(Short applicationDateIndicator) {
        this.applicationDateIndicator = applicationDateIndicator;
    }

    public Integer getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(Integer locationCode) {
        this.locationCode = locationCode;
    }
}