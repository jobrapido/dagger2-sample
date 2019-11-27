package com.jobrapido.blog;


import com.jobrapido.blog.client.ClientsComponent;
import com.jobrapido.blog.client.DaggerClientsComponent;
import com.jobrapido.blog.dto.Gender;
import com.jobrapido.blog.dto.Nationality;

import java.util.Optional;

public class SampleApp {

    public static void main(String... args) {
        final ClientsComponent clientsComponent = DaggerClientsComponent.create();

        final Optional<Gender> stefanoGender = clientsComponent.genderizeClient().genderize("Stefano");
        System.out.println("Gender: " + stefanoGender.orElseThrow().getGenderType());

        final Optional<Nationality> nationality = clientsComponent.nationalizeClient().nationalize("Stefano");
        System.out.println("Nationality: " + nationality.orElseThrow().getCountry());
    }
}
