package com.jobrapido.blog.controller;

import com.jobrapido.blog.dto.Gender;
import com.jobrapido.blog.dto.Nationality;
import com.jobrapido.blog.dto.Person;

public class PersonInfoController {

    public Person getPerson(final String name) {
        return new Person("test",
                new Gender(Gender.GenderType.MALE, 0.0),
                new Nationality("it", 0.0));
    }
}
