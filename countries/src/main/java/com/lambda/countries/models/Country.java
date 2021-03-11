package com.lambda.countries.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name= "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long countryid;

    String name;
    long population;
    long landmasskm2;
    int medianage;

    // Constructors
    public Country() {
    }

    public Country(String name, long population, long landmasskm2, int medianage) {
        this.name = name;
        this.population = population;
        this.landmasskm2 = landmasskm2;
        this.medianage = medianage;
    }

    // Getters and Setters
    public long getCountryId() {
        return countryid;
    }

    public void setCountryId(long countryId) {
        this.countryid = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public long getLandmasskm2() {
        return landmasskm2;
    }

    public void setLandmasskm2(long landmasskm2) {
        this.landmasskm2 = landmasskm2;
    }

    public int getMedianage() {
        return medianage;
    }

    public void setMedianage(int medianage) {
        this.medianage = medianage;
    }

    // toString()
    @Override
    public String toString() {
        return "Country{" +
                "countryId=" + countryid +
                ", name='" + name + '\'' +
                ", population=" + population +
                ", landmasskm2=" + landmasskm2 +
                ", medianage=" + medianage +
                '}';
    }
}
