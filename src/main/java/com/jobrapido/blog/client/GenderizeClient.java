package com.jobrapido.blog.client;

import com.google.gson.Gson;
import com.jobrapido.blog.dto.Gender;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Optional;

@Singleton
public class GenderizeClient extends AbstractClient {

    @Inject
    GenderizeClient(final HttpClient client, final Gson gson) {
        super(client, gson);
    }

    public Optional<Gender> genderize(String name) {
            return send(prepareHttpRequest(name), Gender.class);
    }

    private HttpRequest prepareHttpRequest(final String name) {
        return HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(2))
                .uri(URI.create("https://api.genderize.io?name=".concat(name)))
                .GET()
                .build();
    }
}
