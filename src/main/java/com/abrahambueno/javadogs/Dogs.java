package com.abrahambueno.javadogs;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data // creates getters, setters, to String
@Entity // object ready JPA storage
public class Dogs {
    private @Id @GeneratedValue Long id;
    private String name;
    private double weight;
    private boolean apartment;

    // needed for JPA
    public Dogs() {
        // default contructor
    }

    public Dogs(String name, double weight, boolean apartment) {
        this.name = name;
        this.weight = weight;
        this.apartment = apartment;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isApartment() {
        return apartment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setApartment(boolean apartment) {
        this.apartment = apartment;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
