package com.rudbmsgroupproject.project_2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "msamd")
public class Msamd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msamd_code")
    private Integer code;

    @Column(name = "msamd_name")
    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}