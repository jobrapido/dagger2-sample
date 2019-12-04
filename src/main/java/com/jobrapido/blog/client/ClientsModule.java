package com.jobrapido.blog.client;

import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.net.http.HttpClient;

@Module
public class ClientsModule {

    @Provides
    @Singleton
    GenderizeClient genderizeClient(final HttpClient client, final Gson gson) {
        return new GenderizeClient(client, gson);
    }

    @Provides
    @Singleton
    NationalizeClient nationalizeClient(final HttpClient client, final Gson gson) {
        return new NationalizeClient(client, gson);
    }

}
