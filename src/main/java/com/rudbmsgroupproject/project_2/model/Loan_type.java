package com.rudbmsgroupproject.project_2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "loan_type")
public class Loan_type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_type")
    private Integer loan_type;

    @Column(name = "loan_type_name")
    private String loan_type_name;

    public Integer getLoan_type() {
        return loan_type;
    }

    public void setLoan_type(Integer loan_type) {
        this.loan_type = loan_type;
    }

    public String getLoan_type_name() {
        return loan_type_name;
    }

    public void setLoan_type_name(String loan_type_name) {
        this.loan_type_name = loan_type_name;
    }
}
