package com.jobrapido.blog.client;

import com.google.gson.Gson;
import com.jobrapido.blog.dto.Nationality;
import io.undertow.util.StatusCodes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
//TODO: check why @ExtendWith is not working
class NationalizeClientTest {

    private ArgumentCaptor<HttpRequest> requestArgumentCaptor;

    private HttpClient httpClient;

    private Gson gson = new Gson();

    private NationalizeClient sut;

    @BeforeEach
    void setUp() {
        requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        httpClient = mock(HttpClient.class);
        sut = new NationalizeClient(httpClient, gson);
    }

    @Test
    void shouldReturnParsedNationalityForSuccessfulHttpApiCall() throws IOException, InterruptedException {
        final var httpResponse = mock(HttpResponse.class);

        when(httpResponse.statusCode())
                .thenReturn(StatusCodes.OK);
        when(httpResponse.body())
                .thenReturn("{\"name\":\"michael\",\"country\":[{\"country_id\":\"US\",\"probability\":0.09}]}");

        when(httpClient.send(any(), any()))
                .thenReturn(httpResponse);

        Optional<Nationality> actualNationality = sut.nationalize("michael");

        assertTrue(actualNationality.isPresent());
        assertEquals(new Nationality("US", 0.09), actualNationality.get());
    }

    @Test
    void shouldReturnNationalityWithHighestProbability() throws IOException, InterruptedException {
        final var httpResponse = mock(HttpResponse.class);

        when(httpResponse.statusCode())
                .thenReturn(StatusCodes.OK);
        when(httpResponse.body())
                .thenReturn("{\"name\":\"michael\",\"country\":[{\"country_id\":\"NZ\",\"probability\":0.04}, {\"country_id\":\"US\",\"probability\":0.09}]}");

        when(httpClient.send(any(), any()))
                .thenReturn(httpResponse);

        Optional<Nationality> actualNationality = sut.nationalize("michael");

        assertTrue(actualNationality.isPresent());
        assertEquals(new Nationality("US", 0.09), actualNationality.get());
    }

    @Test
    void shouldReturnEmptyOptionalWhenHttpCallIsNotSuccessful() throws IOException, InterruptedException {
        final var httpResponse = mock(HttpResponse.class);

        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .uri(URI.create("http://test.com"))
                .build();

        when(httpResponse.request())
                .thenReturn(httpRequest);
        when(httpResponse.statusCode())
                .thenReturn(StatusCodes.BAD_REQUEST);

        when(httpClient.send(any(), any()))
                .thenReturn(httpResponse);

        Optional<Nationality> actualNationality = sut.nationalize("michael");

        assertFalse(actualNationality.isPresent());
    }

    @Test
    void shouldCallApiWithCorrectRequestURI() throws IOException, InterruptedException {
        sut.nationalize("michael");

        verify(httpClient).send(requestArgumentCaptor.capture(), any());

        assertEquals(URI.create("https://api.nationalize.io?name=michael"), requestArgumentCaptor.getValue().uri());
    }

    @Test
    void shouldReturnEmptyOptionalWhenHttpClientTrowAInterruptedException() throws IOException, InterruptedException {
        when(httpClient.send(any(), any()))
                .thenThrow(new InterruptedException());

        Optional<Nationality> actualNationality = sut.nationalize("michael");

        assertFalse(actualNationality.isPresent());
    }

    @Test
    void shouldReturnEmptyOptionalWhenHttpClientTrowAIOException() throws IOException, InterruptedException {
        when(httpClient.send(any(), any()))
                .thenThrow(new IOException());

        Optional<Nationality> actualNationality = sut.nationalize("michael");

        assertFalse(actualNationality.isPresent());
    }

}