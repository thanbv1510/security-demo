package com.roninhub.security.controller;

import com.roninhub.security.domain.dto.CommonResponse;
import com.roninhub.security.domain.dto.LoginRequest;
import com.roninhub.security.domain.enumeration.HttpMethod;
import com.roninhub.security.service.UserService;
import com.roninhub.security.util.HttpUtil;
import com.sun.net.httpserver.HttpServer;

public class AuthController extends BaseController {
    private final HttpServer server;
    private final UserService userService;

    public AuthController(HttpServer server, UserService userService) {
        super(userService);

        this.server = server;
        this.userService = userService;
    }

    public void init() {
        server.createContext("/api/login", exchange -> {
            handle(exchange, _ -> {
                if (HttpMethod.POST.name().equals(exchange.getRequestMethod())) {
                    var loginRequest = HttpUtil.getRequestBody(exchange, LoginRequest.class);

                    var response = userService.login(loginRequest);
                    buildResponse(exchange, response.getCode(), response);
                    return;
                }

                // add more method

                buildResponse(exchange, 405, CommonResponse.of(405, "Method not allowed"));
            });

            // create more api
        });
    }
}
