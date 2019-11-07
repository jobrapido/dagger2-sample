package com.jobrapido.blog.client;

import com.jobrapido.blog.module.GsonModule;
import com.jobrapido.blog.module.HttpModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        GsonModule.class,
        HttpModule.class
})
public interface ClientGraph {

    GenderizeClient genderizeClient();

}
