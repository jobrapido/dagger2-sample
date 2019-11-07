package com.jobrapido.blog.module;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

import javax.inject.Singleton;
import java.net.http.HttpClient;

@Module
public class HttpModule {

    @Singleton
    @Provides
    public HttpClient client() {
        return HttpClient.newHttpClient();
    }
}
