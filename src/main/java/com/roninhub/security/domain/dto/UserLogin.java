package com.roninhub.security.domain.dto;

import com.roninhub.security.domain.entity.Role;
import com.roninhub.security.domain.entity.User;
import com.roninhub.security.domain.enumeration.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserLogin {
    private Long id;

    private String username;

    private Set<RoleName> roles;

    public static UserLogin of(final User user) {
        var roles = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet());

        return new UserLogin(user.getId(), user.getPassword(), roles);
    }
}
