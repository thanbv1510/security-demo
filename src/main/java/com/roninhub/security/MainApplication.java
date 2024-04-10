package com.roninhub.security;

import com.roninhub.security.controller.AuthController;
import com.roninhub.security.controller.PostController;
import com.roninhub.security.service.PostService;
import com.roninhub.security.service.UserService;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MainApplication {
    public static void main(String[] args) throws IOException {
        var server = HttpServer.create(new InetSocketAddress(8080), 0);

        // controllers
        var userService = new UserService();
        new AuthController(server, userService).init();

        var postService = new PostService();
        new PostController(server, userService, postService).init();

        // creates a default executor
        server.setExecutor(null);
        server.start();
    }
}