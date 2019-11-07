package com.jobrapido.blog.dto;

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
}
