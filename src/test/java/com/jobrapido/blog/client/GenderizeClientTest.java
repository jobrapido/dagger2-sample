package com.jobrapido.blog.client;

import com.google.gson.Gson;
import com.jobrapido.blog.dto.Gender;
import io.undertow.util.StatusCodes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
class GenderizeClientTest {

    private ArgumentCaptor<HttpRequest> requestArgumentCaptor;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    private Gson gson = new Gson();

    private GenderizeClient sut;

    @BeforeEach
    void setUp() {
        requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        sut = new GenderizeClient(httpClient, gson);
    }

    @Test
    void shouldReturnParsedGenderForSuccessfulHttpApiCall() throws IOException, InterruptedException {
        when(httpResponse.statusCode())
                .thenReturn(StatusCodes.OK);
        when(httpResponse.body())
                .thenReturn("{\"name\": \"peter\", \"gender\": \"male\",\"probability\": 0.99,\"count\": 1}");

        when(httpClient.send(any(), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        Optional<Gender> actualGender = sut.genderize("peter");

        assertTrue(actualGender.isPresent());
        assertEquals(new Gender(Gender.GenderType.MALE, 0.99d), actualGender.get());
    }

    @Test
    void shouldReturnEmptyOptionalWhenHttpCallIsNotSuccessful() throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .uri(URI.create("http://test.com"))
                .build();

        when(httpResponse.request())
                .thenReturn(httpRequest);
        when(httpResponse.statusCode())
                .thenReturn(StatusCodes.BAD_REQUEST);

        when(httpClient.send(any(), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        Optional<Gender> actualGender = sut.genderize("peter");

        assertFalse(actualGender.isPresent());
    }

    @Test
    void shouldCallApiWithCorrectRequestURI() throws IOException, InterruptedException {
        sut.genderize("peter");

        verify(httpClient).send(requestArgumentCaptor.capture(), any());

        assertEquals(URI.create("https://api.genderize.io?name=peter"), requestArgumentCaptor.getValue().uri());
    }

    @Test
    void shouldReturnEmptyOptionalWhenHttpClientTrowAInterruptedException() throws IOException, InterruptedException {
        when(httpClient.send(any(), any()))
                .thenThrow(new InterruptedException());

        Optional<Gender> actualGender = sut.genderize("peter");

        assertFalse(actualGender.isPresent());
    }

    @Test
    void shouldReturnEmptyOptionalWhenHttpClientTrowAIOException() throws IOException, InterruptedException {
        when(httpClient.send(any(), any()))
                .thenThrow(new IOException());

        Optional<Gender> actualGender = sut.genderize("peter");

        assertFalse(actualGender.isPresent());
    }

}