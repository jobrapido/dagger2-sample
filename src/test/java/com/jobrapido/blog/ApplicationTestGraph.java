package com.jobrapido.blog;

import com.google.gson.Gson;
import com.jobrapido.blog.client.ClientsTestModule;
import com.jobrapido.blog.client.GenderizeClient;
import com.jobrapido.blog.client.NationalizeClient;
import dagger.Component;

import javax.inject.Singleton;

@Component(modules = {
        ClientsTestModule.class,
        ThirdPartyModule.class
})
@Singleton
public interface ApplicationTestGraph extends ApplicationGraph {

    GenderizeClient genderizeClient();

    NationalizeClient nationalizeClient();

    Gson gson();
}
