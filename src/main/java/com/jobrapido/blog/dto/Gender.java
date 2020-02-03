package com.jobrapido.blog.dto;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

public class Gender {

    public enum GenderType {
        @SerializedName("male")
        MALE,

        @SerializedName("female")
        FEMALE
    }

    @SerializedName("gender")
    private final GenderType genderType;
    private final double probability;

    public Gender(final GenderType genderType, final double probability) {
        this.genderType = genderType;
        this.probability = probability;
    }

    public GenderType getGenderType() {
        return genderType;
    }

    public double getProbability() {
        return probability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gender gender = (Gender) o;
        return Double.compare(gender.probability, probability) == 0 &&
                genderType == gender.genderType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(genderType, probability);
    }
}
