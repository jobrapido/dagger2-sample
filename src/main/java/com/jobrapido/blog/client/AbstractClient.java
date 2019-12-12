package com.jobrapido.blog.client;

import com.google.common.flogger.FluentLogger;
import com.google.gson.Gson;
import io.undertow.util.StatusCodes;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

abstract class AbstractClient {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private final HttpClient client;
    private final Gson gson;

    AbstractClient(final HttpClient client, final Gson gson) {
        this.client = client;
        this.gson = gson;
    }

    <T> Optional<T> send(final HttpRequest request, final Class<T> clazz) {
        return Optional.ofNullable(send(request))
                .filter(this::checkResponse)
                .map(HttpResponse::body)
                .map(jsonString -> gson.fromJson(jsonString, clazz));
    }

    private HttpResponse<String> send(final HttpRequest httpRequest) {
        try {
            return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException apiException) {
            logger.atSevere().withCause(apiException)
            .log("Failed to retrieve name info from APIs");
        }
        return null;
    }

    private boolean checkResponse(final HttpResponse response) {
        if(response.statusCode() != StatusCodes.OK) {
            logger.atWarning()
                    .log("Call to %s respond with status code %s",
                            response.request().uri().toString(),
                            response.statusCode());
            return false;
        }
        return true;
    }
}
