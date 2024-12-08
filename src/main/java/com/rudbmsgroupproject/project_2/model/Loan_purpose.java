package com.rudbmsgroupproject.project_2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "loan_purpose")
public class Loan_purpose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_purpose")
    private Integer loan_purpose;

    @Column(name = "loan_purpose_name")
    private String loan_purpose_name;

    public Integer getLoan_purpose() {
        return loan_purpose;
    }

    public void setLoan_purpose(Integer loan_purpose) {
        this.loan_purpose = loan_purpose;
    }

    public String getLoan_purpose_name() {
        return loan_purpose_name;
    }

    public void setLoan_purpose_name(String loan_purpose_name) {
        this.loan_purpose_name = loan_purpose_name;
    }
}


