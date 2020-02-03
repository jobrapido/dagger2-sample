package com.jobrapido.blog.client.response;

import com.google.common.base.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NationalizeResponse that = (NationalizeResponse) o;
        return Objects.equal(nationalities, that.nationalities);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nationalities);
    }
}
