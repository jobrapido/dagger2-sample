package com.jobrapido.blog.controller;

import com.jobrapido.blog.module.GsonModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        GsonModule.class
})
public interface ControllerComponent {

    PersonInfoController personInfoController();

}
