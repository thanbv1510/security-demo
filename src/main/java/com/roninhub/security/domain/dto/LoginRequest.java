package com.roninhub.security.domain.dto;

import com.roninhub.security.util.ObjectMapperUtil;
import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;

    public static LoginRequest of(final String request) {
        return ObjectMapperUtil.convertJsonToObject(request, LoginRequest.class).orElse(null);
    }
}
