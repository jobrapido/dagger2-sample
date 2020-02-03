package com.jobrapido.blog;

import com.jobrapido.blog.client.ClientsModule;
import com.jobrapido.blog.controller.PersonInfoController;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        ThirdPartyModule.class,
        ClientsModule.class
})
public interface ApplicationGraph {

    PersonInfoController personInfoController();

}
