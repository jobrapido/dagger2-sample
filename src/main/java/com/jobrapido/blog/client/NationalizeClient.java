package com.jobrapido.blog.client;

import com.google.gson.Gson;
import com.jobrapido.blog.client.response.NationalizeResponse;
import com.jobrapido.blog.dto.Nationality;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Comparator;
import java.util.Optional;

public class NationalizeClient extends AbstractClient {

    NationalizeClient(final HttpClient client, final Gson gson) {
        super(client, gson);
    }

    public Optional<Nationality> nationalize(String name) {
        return send(prepareHttpRequest(name), NationalizeResponse.class)
                .flatMap(this::toNationality);
    }

    private HttpRequest prepareHttpRequest(final String name) {
        return HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(2))
                .uri(URI.create("https://api.nationalize.io?name=".concat(name)))
                .GET()
                .build();
    }

    private Optional<Nationality> toNationality(final NationalizeResponse nationalizeResponse) {
        return nationalizeResponse.getNationalities()
                .stream()
                .max(Comparator.comparingDouble(Nationality::getProbability))
                .map(bestNationalityMatch ->
                        new Nationality(bestNationalityMatch.getCountry(), bestNationalityMatch.getProbability()));
    }
}
