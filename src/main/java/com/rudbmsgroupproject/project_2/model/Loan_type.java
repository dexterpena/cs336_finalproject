package com.rudbmsgroupproject.project_2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "loan_type")
public class Loan_type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_type")
    private Integer id;

    @Column(name = "loan_type_name")
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
