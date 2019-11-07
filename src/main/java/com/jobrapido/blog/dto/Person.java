package com.jobrapido.blog.dto;

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
}
