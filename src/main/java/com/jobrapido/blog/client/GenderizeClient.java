package com.jobrapido.blog.client;

import com.google.gson.Gson;
import com.jobrapido.blog.dto.Gender;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

@Singleton
public class GenderizeClient extends AbstractClient {

    @Inject
    GenderizeClient(final HttpClient client, final Gson gson) {
        super(client, gson);
    }

    public Gender genderize(String name) {
        try {
            return sendRequest(prepareHttpRequest(name), Gender.class)
                    .orElseThrow();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private HttpRequest prepareHttpRequest(final String name) {
        return HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(2))
                .uri(URI.create("https://api.genderize.io?name=".concat(name)))
                .GET()
                .build();
    }
}
