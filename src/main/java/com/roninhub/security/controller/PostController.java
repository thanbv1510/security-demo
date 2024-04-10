package com.roninhub.security.controller;

import com.roninhub.security.domain.dto.CommonResponse;
import com.roninhub.security.domain.enumeration.HttpMethod;
import com.roninhub.security.domain.enumeration.PermissionName;
import com.roninhub.security.domain.enumeration.PostType;
import com.roninhub.security.service.PostService;
import com.roninhub.security.service.UserService;
import com.sun.net.httpserver.HttpServer;

public class PostController extends BaseController {
    private final HttpServer server;
    private final PostService postService;

    public PostController(HttpServer server,
                          UserService userService,
                          PostService postService) {
        super(userService);

        this.server = server;
        this.postService = postService;
    }

    public void init() {

        // require POST_PREMIUM_VIEW permission
        this.server.createContext("/api/post/premium", (exchange -> {
            handle(exchange, _ -> {
                if (HttpMethod.GET.name().equals(exchange.getRequestMethod())) {
                    var response = this.postService.getAllPostByType(PostType.PREMIUM);

                    buildResponse(exchange, response.getCode(), response);
                    return;
                }

                buildResponse(exchange, 405, CommonResponse.of(405, "Method not allowed"));
            }, PermissionName.POST_PREMIUM_VIEW); // add more permission

        }));

        // not require permission
        this.server.createContext("/api/post/free", (exchange -> {
            handle(exchange, _ -> {
                if (HttpMethod.GET.name().equals(exchange.getRequestMethod())) {
                    var response = this.postService.getAllPostByType(PostType.FREE);

                    buildResponse(exchange, response.getCode(), response);
                    return;
                }

                buildResponse(exchange, 405, CommonResponse.of(405, "Method not allowed"));
            });

        }));

    }
}
