package com.jobrapido.blog.client;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

import static org.mockito.Mockito.mock;


@Module
public class ClientsTestModule {

    @Provides
    @Singleton
    protected GenderizeClient genderizeClient() {
        return mock(GenderizeClient.class);
    }

    @Provides
    @Singleton
    protected NationalizeClient nationalizeClient() {
        return mock(NationalizeClient.class);
    }
}
