package com.jobrapido.blog.controller;

import com.google.gson.Gson;
import com.jobrapido.blog.client.ClientsComponent;
import com.jobrapido.blog.client.DaggerClientsComponent;
import com.jobrapido.blog.dto.Person;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Deque;
import java.util.Optional;

@Singleton
public class PersonInfoController implements HttpHandler {

    private final Gson gson;
    private final ClientsComponent clients;

    @Inject
    public PersonInfoController(final Gson gson) {
        this.gson = gson;
        this.clients = DaggerClientsComponent.create();
    }

    @Override
    public void handleRequest(final HttpServerExchange exchange) {
        final Optional<String> nameParam = Optional
                .of(exchange.getQueryParameters())
                .filter(queryParameters -> queryParameters.containsKey("name"))
                .map(queryParameters -> queryParameters.get("name"))
                .map(Deque::getFirst)
                .filter(s -> !s.isBlank());

        nameParam.ifPresentOrElse(name -> exchange
                        .setStatusCode(StatusCodes.OK)
                        .getResponseSender()
                        .send(gson.toJson(new Person(name,
                                clients.genderizeClient().genderize(name).orElse(null),
                                clients.nationalizeClient().nationalize(name).orElse(null)))),
                () -> exchange.setStatusCode(StatusCodes.NOT_FOUND));


    }
}
