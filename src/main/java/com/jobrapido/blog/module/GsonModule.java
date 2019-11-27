package com.jobrapido.blog.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

@Module
public class GsonModule {

    @Reusable
    @Provides
    public Gson gson() {
        return new GsonBuilder().serializeNulls().create();
    }

}
