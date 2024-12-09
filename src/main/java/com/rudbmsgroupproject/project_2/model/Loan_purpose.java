package com.rudbmsgroupproject.project_2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "loan_purpose")
public class Loan_purpose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_purpose")
    private Integer id;

    @Column(name = "loan_purpose_name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}


