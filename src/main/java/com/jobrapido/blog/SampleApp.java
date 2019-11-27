package com.jobrapido.blog;


import com.jobrapido.blog.controller.ControllerComponent;
import com.jobrapido.blog.controller.DaggerControllerComponent;
import io.undertow.Handlers;
import io.undertow.Undertow;

public class SampleApp {

    private static final int HTTP_SERVER_PORT = 8080;
    private static final String HTTP_LISTENING_HOST = "0.0.0.0";

    public static void main(String... args) {
        final ControllerComponent controllerComponent = DaggerControllerComponent.create();

        final Undertow server = Undertow
                .builder()
                .addHttpListener(HTTP_SERVER_PORT, HTTP_LISTENING_HOST)
                .setHandler(
                        Handlers
                                .routing()
                                .get("/person", controllerComponent.personInfoController()))
                .build();

        server.start();
    }
}
