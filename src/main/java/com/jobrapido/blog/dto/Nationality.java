package com.jobrapido.blog.dto;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

public class Nationality {

    @SerializedName("country_id")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nationality that = (Nationality) o;
        return Double.compare(that.probability, probability) == 0 &&
                Objects.equal(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(country, probability);
    }
}
