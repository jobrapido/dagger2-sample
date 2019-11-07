package com.jobrapido.blog.client;

import com.google.gson.Gson;
import io.undertow.util.StatusCodes;

import javax.inject.Inject;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

class AbstractClient {

    private final HttpClient client;
    private final Gson gson;

    AbstractClient(final HttpClient client, final Gson gson) {
        this.client = client;
        this.gson = gson;
    }

    protected <T> Optional<T> sendRequest(final HttpRequest request, final Class<T> clazz) throws IOException, InterruptedException {
        return Optional.of(client.send(request, HttpResponse.BodyHandlers.ofString()))
                .filter(response -> response.statusCode() == StatusCodes.OK)
                .map(HttpResponse::body)
                .map(jsonString -> gson.fromJson(jsonString, clazz));
    }
}
