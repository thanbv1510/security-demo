package com.roninhub.security.domain.dto;

import com.roninhub.security.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private UserLogin user;
    private String token;

    public static LoginResponse of(User user, String token) {
        return new LoginResponse(UserLogin.of(user), token);
    }
}
