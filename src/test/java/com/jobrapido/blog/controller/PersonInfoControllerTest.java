package com.jobrapido.blog.controller;

import com.google.gson.Gson;
import com.jobrapido.blog.client.GenderizeClient;
import com.jobrapido.blog.client.NationalizeClient;
import com.jobrapido.blog.dto.Gender;
import com.jobrapido.blog.dto.Nationality;
import com.jobrapido.blog.dto.Person;
import com.jobrapido.blog.utils.UndertowTestUtils;
import io.undertow.Handlers;
import io.undertow.Undertow;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static com.jobrapido.blog.dto.Gender.GenderType.MALE;
import static com.jobrapido.blog.utils.UndertowTestUtils.buildHttpBaseUrlFromServer;
import static com.jobrapido.blog.utils.UndertowTestUtils.buildServerForRoutes;
import static io.undertow.util.StatusCodes.NOT_FOUND;
import static io.undertow.util.StatusCodes.OK;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class PersonInfoControllerTest {

    private static String httpTestBaseUrl = "";

    private static GenderizeClient genderizeClient;

    private static NationalizeClient nationalizeClient;

    private final static Gson gson = new Gson();

    private static PersonInfoController sut;

    private static Undertow server;

    @BeforeAll
    static void clazzSetUp() {
        genderizeClient = mock(GenderizeClient.class);
        nationalizeClient = mock(NationalizeClient.class);

        sut = new PersonInfoController(gson, genderizeClient, nationalizeClient);

        server = buildServerForRoutes(Handlers
                        .routing()
                        .get("/person", sut));

        server.start();

        httpTestBaseUrl = buildHttpBaseUrlFromServer(server);
    }

    @BeforeEach
    void setUp() {
        doReturn(Optional.of(new Gender(MALE, 0.9)))
                .when(genderizeClient).genderize(anyString());

        doReturn(Optional.of(new Nationality("us", 0.8)))
                .when(nationalizeClient).nationalize(anyString());
    }

    @AfterAll
    static void tearDown() {
        server.stop();
    }

    @Test
    void shouldReturnASuccessfulResponseWithPersonInformation() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> actualHttpResponse = httpClient.send(
                HttpRequest
                        .newBuilder(URI.create(format("%s/person?name=%s", httpTestBaseUrl, "TestName"))).GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString());

        assertEquals(OK, actualHttpResponse.statusCode());
        final Person actualPerson = gson.fromJson(actualHttpResponse.body(), Person.class);
        assertEquals("TestName", actualPerson.getName());
        assertEquals(new Gender(MALE, 0.9), actualPerson.getGender());
        assertEquals(new Nationality("us", 0.8), actualPerson.getNationality());
    }

    @Test
    void shouldReturn404WhenNameParameterIsMissing() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> actualHttpResponse = httpClient.send(
                HttpRequest
                        .newBuilder(URI.create(format("%s/person", httpTestBaseUrl))).GET().build(),
                HttpResponse.BodyHandlers.ofString());

        assertEquals(NOT_FOUND, actualHttpResponse.statusCode());
    }

    @Test
    void shouldReturnNullGenderWhenGenderCouldNotBeResolved() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        doReturn(Optional.empty()).when(genderizeClient).genderize(anyString());

        HttpResponse<String> actualHttpResponse = httpClient.send(
                HttpRequest
                        .newBuilder(URI.create(format("%s/person?name=%s", httpTestBaseUrl, "TestName"))).GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString());

        assertEquals(OK, actualHttpResponse.statusCode());
        final Person actualPerson = gson.fromJson(actualHttpResponse.body(), Person.class);
        assertEquals("TestName", actualPerson.getName());
        assertNull(actualPerson.getGender());
        assertEquals(new Nationality("us", 0.8), actualPerson.getNationality());
    }

    @Test
    void shouldReturnNullNationalityWhenNationalityCouldNotBeResolved() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        doReturn(Optional.empty()).when(nationalizeClient).nationalize(anyString());

        HttpResponse<String> actualHttpResponse = httpClient.send(
                HttpRequest
                        .newBuilder(URI.create(format("%s/person?name=%s", httpTestBaseUrl, "TestName"))).GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString());

        assertEquals(OK, actualHttpResponse.statusCode());
        final Person actualPerson = gson.fromJson(actualHttpResponse.body(), Person.class);
        assertEquals("TestName", actualPerson.getName());
        assertEquals(new Gender(MALE, 0.9), actualPerson.getGender());
        assertNull(actualPerson.getNationality());
    }

}