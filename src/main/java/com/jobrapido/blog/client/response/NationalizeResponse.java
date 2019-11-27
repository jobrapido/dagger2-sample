package com.jobrapido.blog.client.response;

import com.google.gson.annotations.SerializedName;
import com.jobrapido.blog.dto.Nationality;

import java.util.List;

public class NationalizeResponse {

    @SerializedName("country")
    private final List<Nationality> nationalities;

    public NationalizeResponse(final String name,
                               final List<Nationality> nationality) {
        this.nationalities = nationality;
    }

    public List<Nationality> getNationalities() {
        return nationalities;
    }


}
