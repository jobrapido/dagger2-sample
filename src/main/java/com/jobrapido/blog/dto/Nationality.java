package com.jobrapido.blog.dto;

public class Nationality {

    private final String country;
    private final double probability;

    public Nationality(final String country, final double probability) {
        this.country = country;
        this.probability = probability;
    }

    public String getCountry() {
        return country;
    }

    public double getProbability() {
        return probability;
    }
}
