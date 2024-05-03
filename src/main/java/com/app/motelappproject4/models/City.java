// City.java
package com.app.motelappproject4.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "name")
    private String name;

//    @OneToMany(mappedBy = "city")
//    private List<District> districts;

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public City() {

    }

    // Get and Set

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<District> getDistricts() {
//        return districts;
//    }
//
//    public void setDistricts(List<District> districts) {
//        this.districts = districts;
//    }
}
