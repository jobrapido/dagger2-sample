package com.jobrapido.blog.dto;

import com.google.common.base.Objects;

public class Person {

    private final String name;
    private final Gender gender;
    private final Nationality nationality;

    public Person(final String name,
                  final Gender gender,
                  final Nationality nationality) {
        this.name = name;
        this.gender = gender;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Nationality getNationality() {
        return nationality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equal(name, person.name) &&
                Objects.equal(gender, person.gender) &&
                Objects.equal(nationality, person.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, gender, nationality);
    }
}
