package com.roninhub.security.controller;

import com.roninhub.security.domain.dto.CommonResponse;
import com.roninhub.security.domain.enumeration.PermissionName;
import com.roninhub.security.service.UserService;
import com.roninhub.security.util.JwtUtils;
import com.sun.net.httpserver.HttpExchange;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class BaseController {
    private final UserService userService;

    protected void handle(final HttpExchange exchange,
                          final Consumer<HttpExchange> exchangeConsumer,
                          final PermissionName... hasAnyPermission) throws IOException {
        // 1. Check whether the API needs permission or not?
        if (hasAnyPermission.length != 0) {
            // 2. Check if the API sent to the server has a token or not?
            var requestHeaders = exchange.getRequestHeaders();
            if (requestHeaders.isEmpty() || !requestHeaders.containsKey("Authorization")) {
                buildResponse(exchange, 401, CommonResponse.of(401, "Token cannot empty"));
                return;
            }

            final var bearerToken = requestHeaders.getFirst("Authorization");
            if (!bearerToken.startsWith("Bearer ")) {
                buildResponse(exchange, 401, CommonResponse.of(401, "Token is not valid"));
                return;
            }

            final var token = bearerToken.split(" ")[1];

            // 3. Check if the token is valid or not?
            if (!JwtUtils.validateJwtToken(token)) {
                buildResponse(exchange, 401, CommonResponse.of(401, "Token is not valid"));
                exchange.close();
                return;
            }

            var username = JwtUtils.getUserNameFromJwtToken(token);
            // 4. Check whether the User's permissions are valid or not?
            if (!userService.isHasAnyPermission(username, hasAnyPermission)) {
                buildResponse(exchange, 401, CommonResponse.of(401, "user %s does not have permissions [%s]".formatted(username, Arrays.toString(hasAnyPermission))));
                return;
            }
        }

        exchangeConsumer.accept(exchange);
    }

    protected void buildResponse(final HttpExchange exchange,
                                 final int code,
                                 final CommonResponse response) {
        try {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(code, response.toString().getBytes().length);

            var output = exchange.getResponseBody();
            output.write(response.toString().getBytes());

            output.flush();
            exchange.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
