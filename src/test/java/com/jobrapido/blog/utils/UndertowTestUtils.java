package com.jobrapido.blog.utils;

import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;

import java.io.IOException;
import java.net.ServerSocket;

import static java.lang.String.format;

public class UndertowTestUtils {

    private final static String LISTENING_ADDRESS = "127.0.0.1";

    public static Undertow buildServerForRoutes(final RoutingHandler routingHandler) {
        return Undertow.builder()
                .addHttpListener(retrieveFreeRandomPort(), LISTENING_ADDRESS)
                .setHandler(routingHandler)
                .build();
    }

    public static String buildHttpBaseUrlFromServer(final Undertow server) {
        return server.getListenerInfo()
                .stream()
                .findFirst()
                .map(serverInfo ->
                   format("%s:/%s", serverInfo.getProtcol(),
                           serverInfo.getAddress()))
        .orElseThrow(() -> new RuntimeException("Server Listener Info could not be retrieved"));
    }

    private static int retrieveFreeRandomPort() {
        int randomPort;
        try {
            ServerSocket socket = new ServerSocket(0);
            randomPort = socket.getLocalPort();
            socket.close();
        } catch (final IOException e) {
            throw new RuntimeException("Failed retrieving random free listening port", e);
        }
        return randomPort;
    }
}
