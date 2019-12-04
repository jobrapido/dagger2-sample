package com.jobrapido.blog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

import javax.inject.Singleton;
import java.net.http.HttpClient;

@Module
public class ThirdPartyModule {

    @Reusable
    @Provides
    public Gson gson() {
        return new GsonBuilder().serializeNulls().create();
    }

    @Singleton
    @Provides
    public HttpClient client() {
        return HttpClient.newHttpClient();
    }
}
