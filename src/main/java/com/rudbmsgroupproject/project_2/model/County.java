package com.rudbmsgroupproject.project_2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "county")
public class County {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "county_code")
    private Integer id;

    @Column(name = "county_name")
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
