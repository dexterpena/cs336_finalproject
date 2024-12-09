package com.rudbmsgroupproject.project_2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "property_type")
public class PropertyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_type")
    private Integer id;

    @Column(name = "property_type_name")
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
