package com.jobrapido.blog.controller;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.jobrapido.blog.client.GenderizeClient;
import com.jobrapido.blog.client.NationalizeClient;
import com.jobrapido.blog.dto.Gender;
import com.jobrapido.blog.dto.Nationality;
import io.undertow.io.Sender;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Optional;

import static com.jobrapido.blog.dto.Gender.GenderType.MALE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersonInfoControllerTest {

    private GenderizeClient genderizeClient;

    private NationalizeClient nationalizeClient;

    private final Gson gson = new Gson();

    private PersonInfoController sut;

    @BeforeEach
    void setUp() {
        genderizeClient = mock(GenderizeClient.class);
        nationalizeClient = mock(NationalizeClient.class);
        sut = new PersonInfoController(gson, genderizeClient, nationalizeClient);

        when(genderizeClient.genderize(anyString()))
                .thenReturn(Optional.of(new Gender(MALE, 0.9)));

        when(nationalizeClient.nationalize(anyString()))
                .thenReturn(Optional.of(new Nationality("us", 0.8)));
    }

    @Test
    //TODO: not working
    void shouldReturnASuccessfulResponseWithPersonInformation() {
        HttpServerExchange exchange = mock(HttpServerExchange.class);
        Sender sender = mock(Sender.class);
        when(exchange.getResponseSender())
                .thenReturn(sender);
        when(exchange.setStatusCode(any()))
                .thenReturn(exchange);
        when(exchange.getQueryParameters())
                .thenReturn(ImmutableMap.of("name",
                        new ArrayDeque<>(ImmutableList.of("peter"))));

        sut.handleRequest(exchange);

        verify(exchange).setStatusCode(StatusCodes.OK);
        verify(exchange.getResponseSender()).send("{\"name\":\"peter\"," +
                "\"gender\":{\"gender\":\"male\",\"probability\":0.9}," +
                "\"nationality\":{\"country_id\":\"us\",\"probability\":0.8}}\n");
    }


}